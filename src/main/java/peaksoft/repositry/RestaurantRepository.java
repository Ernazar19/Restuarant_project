package peaksoft.repositry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r")
    Page<RestaurantResponse> findAllRestaurants(Pageable pageable);
}
