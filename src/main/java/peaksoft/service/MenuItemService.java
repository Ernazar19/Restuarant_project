package peaksoft.service;

import org.springframework.data.repository.query.Param;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SearchResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.dto.response.pagination.PaginationResponseMenuItem;

import java.util.List;

public interface MenuItemService {
    SimpleResponse save(MenuItemRequest request);

    SimpleResponse update(Long id, MenuItemRequest menuItemRequest);

    MenuItemResponse getById(Long id);

    SimpleResponse  delete(Long id);

    PaginationResponseMenuItem getAllMenuItems(int pageSize, int currentPage);

    List<SearchResponse> search(String keyword);

    List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(String sort);
    List<MenuItemResponse> filter(@Param("isVegetarian") Boolean isVegetarian);


}
