package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.enums.RestaurantType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;
    private int services;
}
