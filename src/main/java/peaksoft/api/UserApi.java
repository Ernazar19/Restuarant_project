package peaksoft.api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.service.UserService;
import java.util.List;
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;


    @PostMapping("/singUp")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return userService.signUp(signUpRequest);
    }
    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody SignInRequest signIn){
        return userService.signIn(signIn);
    }
    @GetMapping("/get/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public PaginationResponseUser getAll(int pageSize, int currentPage) {
        return userService.getAllUsers(pageSize,currentPage);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id,userRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}

