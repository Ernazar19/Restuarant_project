package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;
@Builder
public record PaginationResponseSubCategory(
        List<SubCategoryResponse> subCategoryResponseList,
        int size,
        int page
) {
}
