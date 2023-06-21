package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.enums.RestaurantType;

@Data
@Builder
@NoArgsConstructor

public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private RestaurantType restaurantType;
    private int numberOfEmployees;
    private int services;

    public RestaurantResponse(Long id, String name, String location, RestaurantType restaurantType, int numberOfEmployees, int services) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restaurantType = restaurantType;
        this.numberOfEmployees = numberOfEmployees;
        this.services = services;
    }
}
