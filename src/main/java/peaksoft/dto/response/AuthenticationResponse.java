package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private String email;
    private Role role;

    public AuthenticationResponse(String token, String email, Role role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }
}