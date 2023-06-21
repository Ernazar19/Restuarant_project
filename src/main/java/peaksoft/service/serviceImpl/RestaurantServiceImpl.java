package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.PaginationResponseRestaurant;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.entity.Restaurant;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositry.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        int countRestaurant = restaurantRepository.findAll().size();
        if (countRestaurant < 1) {
            if (restaurantRequest.getName().isBlank() ||
                    restaurantRequest.getRestaurantType().describeConstable().isEmpty() ||
                    restaurantRequest.getServices() == 0 ||
                    restaurantRequest.getLocation().isBlank()) {
                throw new BadRequestException("When saving the restaurant, one of the columns remained empty!");
            } else {
                Restaurant restaurant = new Restaurant(restaurantRequest.getName(),
                        restaurantRequest.getLocation(),
                        restaurantRequest.getRestaurantType(),
                        0,
                        restaurantRequest.getServices());
                restaurantRepository.save(restaurant);
                return SimpleResponse.builder().
                        status(HttpStatus.OK).
                        message(String.format("Restaurant with name: %s successfully saved!", restaurantRequest.getName()))
                        .build();
            }
        } else {
            throw new BadRequestException("You can't save multiple restaurants!");
        }

    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).
                    orElseThrow(() -> new NotFoundException(String.format("Restaurant with id: %s is not found", restaurantId)));
            return RestaurantResponse.builder()
                    .id(restaurant.getId()).
                    name(restaurant.getName())
                    .location(restaurant.getLocation())
                    .restaurantType(restaurant.getRestType())
                    .services(restaurant.getService()).
                    build();
        } catch (NotFoundException n) {
            System.out.println(n.getMessage());
        }
        return null;
    }

    @Override
    public SimpleResponse deleteRestaurantById(Long id) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id).
                    orElseThrow(() -> new NotFoundException(String.format("Restaurant with id: %s is not found", id)));
            restaurantRepository.delete(restaurant);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: %s is successfully deleted", id))
                .build();
    }

    @Override
    public PaginationResponseRestaurant getAllRestaurants(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<RestaurantResponse> allRestaurants=restaurantRepository.findAllRestaurants(pageable);
        return PaginationResponseRestaurant
                .builder()
                .restaurantResponses(allRestaurants.getContent())
                .page(allRestaurants.getNumber()+1)
                .size(allRestaurants.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format
                        ("Restaurant with id: %s is not found", id)));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestaurantType());
        restaurant.setService(restaurantRequest.getServices());
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant whit id: %s is successfully updated!",id))
                .build();
    }
}
