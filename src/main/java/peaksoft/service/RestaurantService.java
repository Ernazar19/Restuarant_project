package peaksoft.service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.dto.response.pagination.PaginationResponseRestaurant;

import java.util.List;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse getRestaurantById(Long restaurantId);
    SimpleResponse deleteRestaurantById(Long id);
    PaginationResponseRestaurant getAllRestaurants(int pageSize, int currentPage);
    SimpleResponse updateRestaurant(Long id,RestaurantRequest restaurantRequest);
}
