package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.pagination.PaginationResponseStopList;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositry.MenuItemRepository;
import peaksoft.repositry.StopListRepository;
import peaksoft.service.StopListService;

import java.time.LocalDate;


@Service
@Transactional
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public PaginationResponseStopList getAllStopList(int pageSize, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<StopListResponse> allStopList = stopListRepository.findAllStopLists(pageable);
        return PaginationResponseStopList
                .builder()
                .stopListResponseList(allStopList.getContent())
                .page(allStopList.getNumber() + 1)
                .size(allStopList.getTotalPages())
                .build();
    }

    public SimpleResponse save(Long menuId, StopListRequest request) {
        int date1 = request.getDate().compareTo(LocalDate.now());
        if (date1 >= 0) {
            MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(() ->
                    new NotFoundException(String.format("Menu Item with id :%s already exists!", menuId)));
            for (StopList stopList : stopListRepository.findAll()) {
                int date = stopList.getDate().compareTo(request.getDate());
                if (date == 0) {
                    if (stopList.getMenuItem().getId() == menuItem.getId()) {
                        throw new BadRequestException("Save Date Exception!");
                    }
                }
            }
            StopList stopList = new StopList(
                    request.getReason(),
                    request.getDate()
            );

            stopList.setMenuItem(menuItem);
            stopListRepository.save(stopList);
            return SimpleResponse
                    .builder().status(HttpStatus.OK)
                    .message(String.format("StopList %s successfully saved", request.getMenuItemName()))
                    .build();
        } else {
            throw new BadRequestException("date exception!");
        }
    }

    @Override
    public StopListResponse findById(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                new NotFoundException("StopList with id : " + id + "is not found!"));
        return StopListResponse.builder()
                .id(stopList.getId())
                .date(stopList.getDate())
                .reason(stopList.getReason())
                .build();
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StopList with id : " + id + "is not found!"));
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());
        stopListRepository.save(stopList);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with MenuItemName : %s " + "successfully update",
                        stopListRequest.getMenuItemName()))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!stopListRepository.existsById(id)) {
            throw new NotFoundException("StopList with id : " + id + "is not found");
        }
        stopListRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with id : %s is deleted!", id)).build();
    }
}