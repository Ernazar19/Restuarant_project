package peaksoft.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserResponse> getUserById(Long id);

    @Query("select  new  peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role ) from User u")
    Page<UserResponse> findAllUsers(Pageable pageable);

    Boolean existsByPhoneNumber(String phoneNumber);
}
