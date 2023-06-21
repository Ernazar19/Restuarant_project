package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseMenuItem;
import peaksoft.dto.response.pagination.PaginationResponseRestaurant;
import peaksoft.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @GetMapping("/getAllRestaurants")
    public PaginationResponseRestaurant findAllRestaurants(int pageSize, int currentPage){
        return restaurantService.getAllRestaurants(pageSize,currentPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  RestaurantResponse findById( @PathVariable Long id){
        return restaurantService.getRestaurantById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  SimpleResponse updateRestaurantById(@PathVariable Long id ,
                                                @RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.updateRestaurant(id, restaurantRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteRestaurantById(@PathVariable Long id){
        return restaurantService.deleteRestaurantById(id);
    }

}