package peaksoft.service;

import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.dto.response.pagination.PaginationResponseUser;

import java.util.List;

public interface UserService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);
    UserResponse getUserById(Long id);
    PaginationResponseUser getAllUsers(int pageSize, int currentPage);
    SimpleResponse deleteUser(Long id);
    SimpleResponse updateUser(Long id, UserRequest userRequest);
    
}
