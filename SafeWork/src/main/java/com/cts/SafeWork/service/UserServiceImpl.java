//
//
//package com.cts.SafeWork.service;
//
//import com.cts.SafeWork.dto.UserPublicDTO;
//import com.cts.SafeWork.dto.UserUpdateDTO;
//import com.cts.SafeWork.entity.Employee;
//import com.cts.SafeWork.entity.User;
//import com.cts.SafeWork.exception.CustomException;
//import com.cts.SafeWork.repository.EmployeeRepository;
//import com.cts.SafeWork.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class UserServiceImpl implements IUserService {
//
//    private final UserRepository userRepository;
//    private final EmployeeRepository employeeRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final JWTService jwtService;
//
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository,
//                           EmployeeRepository employeeRepository,
//                           PasswordEncoder passwordEncoder,
//                           AuthenticationManager authenticationManager,
//                           JWTService jwtService) {
//        this.userRepository = userRepository;
//        this.employeeRepository = employeeRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.jwtService = jwtService;
//    }
//
//    @Override
//    public UserPublicDTO loginUser(String email, String password) {
//        log.info("Attempting login for: {}", email);
//
//        // 1. Authenticate (Spring checks the password via AuthenticationManager)
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, password));
//
//        if (auth.isAuthenticated()) {
//            // 2. Check User Table first (Officers/Admin)
//            Optional<User> userOpt = userRepository.findByUserEmail(email);
//            if (userOpt.isPresent()) {
//                User user = userOpt.get();
//                String role = user.getUserRole().name(); // Matches SecurityConfig (e.g. ADMIN)
//                String token = jwtService.generateToken(user.getUserEmail(), role);
//
//                UserPublicDTO dto = toDTO(user);
//                dto.setToken(token);
//                return dto;
//            }
//
//            // 3. Check Employee Table
//            Optional<Employee> empOpt = employeeRepository.findByEmail(email);
//            if (empOpt.isPresent()) {
//                Employee emp = empOpt.get();
//                // We use "EMPLOYEE" as the role to match SecurityConfig .hasAuthority("EMPLOYEE")
//                String token = jwtService.generateToken(emp.getEmail(), "EMPLOYEE");
//
//                UserPublicDTO dto = new UserPublicDTO();
//                dto.setToken(token);
//                dto.setUserName(emp.getEmployeeName());
//                dto.setUserEmail(emp.getEmail());
//                dto.setUserRole("EMPLOYEE");
//                return dto;
//            }
//        }
//        throw new CustomException("Login Failed: Invalid credentials");
//    }
//
//    @Override
//    public User registerUser(User user) {
//        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
//            throw new CustomException("User with this email already exists");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User createUser(User user) {
//        return registerUser(user);
//    }
//
//    @Override
//    public UserPublicDTO updateUser(Long userId, UserUpdateDTO dto) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new CustomException("User not found with id: " + userId));
//
//        if (dto.getUserName() != null) user.setUserName(dto.getUserName());
//        if (dto.getUserEmail() != null) user.setUserEmail(dto.getUserEmail());
//        if (dto.getUserRole() != null) user.setUserRole(dto.getUserRole());
//
//        userRepository.save(user);
//        return toDTO(user);
//    }
//
//    @Override
//    public void deleteUser(Long userId) {
//        userRepository.deleteById(userId);
//    }
//
//    @Override
//    public UserPublicDTO getUserById(Long userId) {
//        return userRepository.findById(userId).map(this::toDTO).orElseThrow(() -> new CustomException("Not found"));
//    }
//
//    @Override
//    public List<UserPublicDTO> getAllUsers() {
//        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public UserPublicDTO getUserByEmail(String userEmail) {
//        return userRepository.findByUserEmail(userEmail).map(this::toDTO).orElseThrow(() -> new CustomException("Not found"));
//    }
//
//    @Override
//    public UserPublicDTO getUserByName(String userName) {
//        return userRepository.findByUserName(userName).map(this::toDTO).orElseThrow(() -> new CustomException("Not found"));
//    }
//
//    private UserPublicDTO toDTO(User user) {
//        UserPublicDTO dto = new UserPublicDTO();
//        dto.setUserName(user.getUserName());
//        dto.setUserEmail(user.getUserEmail());
//        dto.setUserContact(user.getUserContact());
//        dto.setUserStatus(user.getUserStatus());
//        dto.setUserRole(user.getUserRole().name());
//        return dto;
//    }
//}
//
//
//


package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.UserPublicDTO;
import com.cts.SafeWork.dto.UserUpdateDTO;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.exception.CustomException;
import com.cts.SafeWork.repository.EmployeeRepository;
import com.cts.SafeWork.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmployeeRepository employeeRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JWTService jwtService) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserPublicDTO loginUser(String email, String password) {
        log.info("Login attempt initiated for user: {}", email);

        try {
            // 1. Authenticate (Spring checks the password via AuthenticationManager)
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            if (auth.isAuthenticated()) {
                log.debug("Authentication successful for: {}", email);

                // 2. Check User Table first (Officers/Admin)
                Optional<User> userOpt = userRepository.findByUserEmail(email);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    String role = user.getUserRole().name();
                    log.info("User found in USER table with role: {}", role);

                    String token = jwtService.generateToken(user.getUserEmail(), role);
                    UserPublicDTO dto = toDTO(user);
                    dto.setToken(token);
                    return dto;
                }

                // 3. Check Employee Table
                Optional<Employee> empOpt = employeeRepository.findByEmail(email);
                if (empOpt.isPresent()) {
                    Employee emp = empOpt.get();
                    log.info("User found in EMPLOYEE table: {}", emp.getEmployeeName());

                    String token = jwtService.generateToken(emp.getEmail(), "EMPLOYEE");
                    UserPublicDTO dto = new UserPublicDTO();
                    dto.setToken(token);
                    dto.setUserName(emp.getEmployeeName());
                    dto.setUserEmail(emp.getEmail());
                    dto.setUserRole("EMPLOYEE");
                    return dto;
                }
            }
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user {}: {}", email, e.getMessage());
            throw new CustomException("Login Failed: Invalid credentials");
        }

        log.warn("Authentication passed but user record not found in database for: {}", email);
        throw new CustomException("Login Failed: User record missing");
    }

    @Override
    public User registerUser(User user) {
        log.info("Registering new user with email: {}", user.getUserEmail());

        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
            log.warn("Registration failed: Email {} already exists", user.getUserEmail());
            throw new CustomException("User with this email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User successfully registered with ID: {}", savedUser.getUserId());
        return savedUser;
    }

    @Override
    public User createUser(User user) {
        return registerUser(user);
    }

    @Override
    public UserPublicDTO updateUser(Long userId, UserUpdateDTO dto) {
        log.info("Updating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Update failed: User ID {} not found", userId);
                    return new CustomException("User not found with id: " + userId);
                });

        if (dto.getUserName() != null) user.setUserName(dto.getUserName());
        if (dto.getUserEmail() != null) user.setUserEmail(dto.getUserEmail());
        if (dto.getUserRole() != null) {
            log.debug("Updating role to: {} for user ID: {}", dto.getUserRole(), userId);
            user.setUserRole(dto.getUserRole());
        }

        userRepository.save(user);
        log.info("User ID {} successfully updated", userId);
        return toDTO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Request to delete user ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            log.warn("Delete failed: User ID {} does not exist", userId);
            throw new CustomException("User not found");
        }
        userRepository.deleteById(userId);
        log.info("User ID {} deleted successfully", userId);
    }

    @Override
    public UserPublicDTO getUserById(Long userId) {
        log.debug("Fetching user by ID: {}", userId);
        return userRepository.findById(userId)
                .map(this::toDTO)
                .orElseThrow(() -> {
                    log.error("Fetch failed: User ID {} not found", userId);
                    return new CustomException("Not found");
                });
    }

    @Override
    public List<UserPublicDTO> getAllUsers() {
        log.info("Fetching all users list");
        List<UserPublicDTO> users = userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        log.debug("Total users retrieved: {}", users.size());
        return users;
    }

    @Override
    public UserPublicDTO getUserByEmail(String userEmail) {
        log.debug("Fetching user by email: {}", userEmail);
        return userRepository.findByUserEmail(userEmail)
                .map(this::toDTO)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", userEmail);
                    return new CustomException("Not found");
                });
    }

    @Override
    public UserPublicDTO getUserByName(String userName) {
        log.debug("Fetching user by name: {}", userName);
        return userRepository.findByUserName(userName)
                .map(this::toDTO)
                .orElseThrow(() -> {
                    log.warn("User not found with name: {}", userName);
                    return new CustomException("Not found");
                });
    }

    private UserPublicDTO toDTO(User user) {
        UserPublicDTO dto = new UserPublicDTO();
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserContact(user.getUserContact());
        dto.setUserStatus(user.getUserStatus());
        dto.setUserRole(user.getUserRole().name());
        return dto;
    }
}