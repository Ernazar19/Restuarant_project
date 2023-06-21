package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;
@Builder
public record PaginationResponseCheque(
        List<ChequeResponse> chequeResponseList,
        int size,
        int page
) {
}
