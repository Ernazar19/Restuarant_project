package peaksoft.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.enums.Role;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor

public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private Role role;

    public UserResponse(Long id, String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
