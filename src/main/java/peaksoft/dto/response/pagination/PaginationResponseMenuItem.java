package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;
@Builder
public record PaginationResponseMenuItem(
        List<MenuItemResponse> menuItemResponseList,
        int size,
        int page
) {
}
