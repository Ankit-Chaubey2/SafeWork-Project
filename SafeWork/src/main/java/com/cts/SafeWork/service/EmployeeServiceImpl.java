package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.enums.EmployeeStatus;
import com.cts.SafeWork.exception.EmployeeNotFoundException;
import com.cts.SafeWork.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    //it check the provided data with the DB and match the encoded assword with provided
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Override
    public EmployeeResponseDTO loginEmployee(LoginRequestDTO loginRequest) {
        log.info("Employee login attempt for email: {}", loginRequest.getEmail());

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            if (auth.isAuthenticated()) {
                Employee emp = employeeRepository.findByEmail(loginRequest.getEmail())
                        .orElseThrow(() -> new EmployeeNotFoundException("Employee record not found."));

                String token = jwtService.generateToken(emp.getEmail(), "EMPLOYEE");
                EmployeeResponseDTO response = mapToResponseDTO(emp);
                response.setToken(token);
                return response;
            }
        } catch (AuthenticationException e) {
            throw new EmployeeNotFoundException("Invalid credentials!");
        }
        throw new EmployeeNotFoundException("Invalid credentials!");
    }

    @Override
    public EmployeeResponseDTO registerEmployee(EmployeeRegistrationDTO dto) {
        log.info("Registering new employee: {}", dto.getEmail());

        Employee employee = new Employee();
        employee.setEmail(dto.getEmail());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setEmployeeName(dto.getEmployeeName());
        employee.setEmployeeDOB(dto.getEmployeeDOB());
        employee.setEmployeeGender(dto.getEmployeeGender());
        employee.setEmployeeAddress(dto.getEmployeeAddress());
        employee.setEmployeeContact(dto.getEmployeeContact());
        employee.setEmployeeDepartmentName(dto.getEmployeeDepartmentName());
        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);

        Employee saved = employeeRepository.save(employee);
        return mapToResponseDTO(saved);
    }

//    @Override
//    public EmployeeResponseDTO getEmployeeById(Long id) {
////security context holder is use to access te details of the user currently making req
//        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
//
//        if (!employee.getEmail().equals(currentUserEmail)) {
//            log.warn("Unauthorized access! User {} tried to view data for ID {}", currentUserEmail, id);
//            throw new RuntimeException("Access Denied: You can only view your own details.");
//        }
//
//        return mapToResponseDTO(employee);
//    }

        @Override
        public EmployeeResponseDTO getEmployeeById(Long id) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = auth.getName();

            // Check if the user has ADMIN authority
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ADMIN"));

            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

            // Allow if it's the owner OR if it's an admin
            if (!isAdmin && !employee.getEmail().equals(currentUserEmail)) {
                log.warn("Unauthorized access! User {} tried to view data for ID {}", currentUserEmail, id);
                throw new RuntimeException("Access Denied: You can only view your own details.");
            }

            return mapToResponseDTO(employee);
        }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRegistrationDTO details) {
        // SECURITY FIX: Prevent updating someone else's profile
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        if (!employee.getEmail().equals(currentUserEmail)) {
            log.error("Unauthorized update attempt! User {} tried to modify ID {}", currentUserEmail, id);
            throw new RuntimeException("Access Denied: You can only update your own details.");
        }

        employee.setEmployeeName(details.getEmployeeName());
        employee.setEmployeeAddress(details.getEmployeeAddress());
        employee.setEmployeeContact(details.getEmployeeContact());

        Employee updated = employeeRepository.save(employee);
        return mapToResponseDTO(updated);
    }

    @Override
    @Transactional
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        // SECURITY FIX: Ensure password change is for the logged-in user
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));

        if (!employee.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Access Denied: You cannot change someone else's password.");
        }

        if (passwordEncoder.matches(oldPassword, employee.getPassword())) {
            employee.setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    private EmployeeResponseDTO mapToResponseDTO(Employee emp) {
        EmployeeResponseDTO response = new EmployeeResponseDTO();
        response.setEmployeeId(emp.getEmployeeId());
        response.setEmail(emp.getEmail());
        response.setEmployeeName(emp.getEmployeeName());
        response.setEmployeeDOB(emp.getEmployeeDOB());
        response.setEmployeeGender(emp.getEmployeeGender());
        response.setEmployeeAddress(emp.getEmployeeAddress());
        response.setEmployeeContact(emp.getEmployeeContact());
        response.setEmployeeDepartmentName(emp.getEmployeeDepartmentName());
        response.setEmployeeStatus(emp.getEmployeeStatus());
        return response;
    }
}