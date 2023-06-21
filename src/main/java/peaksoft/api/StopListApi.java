package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.dto.response.pagination.PaginationResponseStopList;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.exceptions.BadRequestException;
import peaksoft.service.StopListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stopLists")
public class StopListApi {
    private final StopListService stopListService;

    @PostMapping("/save/{menuId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@PathVariable Long menuId, @RequestBody StopListRequest request) {
        return stopListService.save(menuId, request);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public PaginationResponseStopList getAllCategories(int pageSize, int currentPage) {
        return stopListService.getAllStopList(pageSize,currentPage);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id){
        return stopListService.deleteById(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public StopListResponse getById(@PathVariable Long id){
        return stopListService.findById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF' )")
    public SimpleResponse update(@PathVariable Long id,@RequestBody StopListRequest request){
        return stopListService.update(id, request);
    }

}