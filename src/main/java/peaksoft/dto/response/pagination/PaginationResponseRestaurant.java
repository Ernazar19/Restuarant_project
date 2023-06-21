package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;
@Builder
public record PaginationResponseRestaurant (
        List<RestaurantResponse> restaurantResponses,
        int size,
        int page
){
}
