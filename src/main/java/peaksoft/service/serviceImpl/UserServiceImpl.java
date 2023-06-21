package peaksoft.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositry.UserRepository;
import peaksoft.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EntityExistsException(String.format(
                    "User with email: %s already exists!", signUpRequest.getEmail()));
        }

        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {

        User user = userRepository.getUserByEmail(signInRequest.getEmail()).orElseThrow(() -> new NotFoundException("User not found!"));
        if (signInRequest.getPassword().isBlank()) {
            throw new BadRequestException("Password is empty!");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(),user.getPassword())) {
            throw new BadCredentialException("Wrong password!");
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    @PostConstruct
    public void init() {
        User user3 = new User();
        user3.setFirstName("Ernazar");
        user3.setLastName("Asanbekov");
        user3.setDateOfBirth(LocalDate.of(2003,1,19));
        user3.setEmail("ernazar@gmail.com");
        user3.setPassword(passwordEncoder.encode("ernazar664"));
        user3.setPhoneNumber("+996707403513");
        user3.setRole(Role.ADMIN);
        user3.setExperience(1);
        if (!userRepository.existsByEmail(user3.getEmail())) {
            userRepository.save(user3);
        }
    }
    @Override
    public UserResponse getUserById(Long id) {
        try {

            User user = userRepository.findById(id).
                    orElseThrow(() -> new NotFoundException(String.format("User with id: %s is not found", id)));
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(LocalDate.from(user.getDateOfBirth()))
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole()).build();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public PaginationResponseUser getAllUsers(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<UserResponse>allUsers=userRepository.findAllUsers(pageable);
        return PaginationResponseUser
                .builder()
                .userResponseList(allUsers.getContent())
                .page(allUsers.getNumber()+1)
                .size(allUsers.getTotalPages())
                .build();
    }


    @Override
    public SimpleResponse deleteUser(Long id) {
        if (id != 1L) {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Author with email :%s already exists", id)));
            userRepository.delete(user);
            return SimpleResponse.builder().status(HttpStatus.OK).message("User with id: %s is successfully deleted").build();
        } else {
            throw new BadRequestException("user by id 1L dont deleted");
        }
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id :%s is not found", id)));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id :%s is successfully updated", id))
                .build();

    }
}
