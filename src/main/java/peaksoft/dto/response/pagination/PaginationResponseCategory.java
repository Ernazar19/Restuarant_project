package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.CategoryResponse;


import java.util.List;
@Builder
public record PaginationResponseCategory (
        List<CategoryResponse> categoryResponseList,
        int size,
        int page
){
}
