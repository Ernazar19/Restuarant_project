package peaksoft.service.serviceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SearchResponse;
import peaksoft.dto.response.SimpleResponse;

import peaksoft.dto.response.pagination.PaginationResponseMenuItem;

import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositry.MenuItemRepository;
import peaksoft.repositry.RestaurantRepository;
import peaksoft.repositry.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final SubCategoryRepository subcategoryRepository;
    private final RestaurantRepository restaurantRepository;



    @Override
        public SimpleResponse save(MenuItemRequest request) {
        if (request.getName().isBlank() &&
                request.getImage().isBlank() &&
                request.getPrice() == 0 &&
                request.getDescription().isBlank()) {
            throw new BadRequestException("");
        } else {
            Restaurant restaurant1 = null;
            for (Restaurant restaurant : restaurantRepository.findAll()) {
                restaurant1 = restaurant;
            }
            SubCategory subcategory = subcategoryRepository.findById(request.getSubcategoryId()).orElseThrow(() -> new NotFoundException("N Subcategory By Id " + request.getSubcategoryId()));
            MenuItem menuItem = new MenuItem(
                    request.getName(),
                    request.getImage(),
                    request.getPrice(),
                    request.getDescription(),
                    request.getIsVegetarian()
            );
            menuItem.setSubcategories(subcategory);
            menuItem.setRestaurant(restaurant1);
            menuItemRepository.save(menuItem);
            return null;
        }

    }
    @Override
    public SimpleResponse update(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem1 = menuItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("MenuItem with id : " + id + "is not found!"));
        menuItem1.setName(menuItemRequest.getName());
        menuItem1.setDescription(menuItemRequest.getDescription());
        menuItem1.setImage(menuItemRequest.getImage());
        menuItem1.setPrice(menuItemRequest.getPrice());
        menuItemRepository.save(menuItem1);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK).
                message(String.format("MenuItem with name : %s " +
                                "successfully update",
                        menuItemRequest.getName()))
                .build();
    }


    @Override
    public MenuItemResponse getById(Long id) {
        return menuItemRepository.getMenuItemById(id).orElseThrow(
                () -> new NotFoundException("MenuItem with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new NotFoundException("MenuItem with id : " + id + "is not found");
        }
        menuItemRepository.deleteById(id);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with id : %s is deleted!", id))
                .build();
    }


    @Override
    public PaginationResponseMenuItem getAllMenuItems(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<MenuItemResponse> allMenuItem=menuItemRepository.findAllMenuItems(pageable);
        return PaginationResponseMenuItem
                .builder()
                .menuItemResponseList(allMenuItem.getContent())
                .page(allMenuItem.getNumber()+1)
                .size(allMenuItem.getTotalPages())
                .build();

    }

    @Override
    public List<SearchResponse> search(String keyword) {
        return menuItemRepository.search(keyword);
    }

    @Override
    public List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(String sort) {
        if (sort.equalsIgnoreCase("asc")){
            return menuItemRepository.getAllByOrderByPriceAsc();
        }else if (sort.equalsIgnoreCase("desc")) {
            return menuItemRepository.getAllByOrderByPriceDesc();
        }else
            return new ArrayList<>();
    }

    @Override
    public List<MenuItemResponse> filter(Boolean isVegetarian) {
        return menuItemRepository.filter(isVegetarian);
    }
}
