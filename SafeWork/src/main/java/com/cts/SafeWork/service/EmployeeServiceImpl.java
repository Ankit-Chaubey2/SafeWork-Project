package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.enums.EmployeeStatus;
import com.cts.SafeWork.exception.EmployeeNotFoundException;
import com.cts.SafeWork.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j; // Log import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j // Lombok annotation for logging
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public EmployeeResponseDTO registerEmployee(EmployeeRegistrationDTO dto) {
        log.info("Attempting to register new employee with email: {}", dto.getEmail());

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
        log.info("Employee registered successfully. Generated ID: {}", saved.getEmployeeId());
        return mapToResponseDTO(saved);
    }

    @Override
    public EmployeeResponseDTO loginEmployee(LoginRequestDTO loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());

        Employee employee = employeeRepository.findByEmail(loginRequest.getEmail())
                .filter(emp -> passwordEncoder.matches(loginRequest.getPassword(), emp.getPassword()))
                .orElseThrow(() -> {
                    log.error("Login failed: Invalid credentials for email: {}", loginRequest.getEmail());
                    return new EmployeeNotFoundException("Invalid email or password!");
                });

        log.info("Login successful for employee ID: {}", employee.getEmployeeId());
        return mapToResponseDTO(employee);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        log.info("Fetching details for employee ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee fetch failed: ID {} not found", id);
                    return new EmployeeNotFoundException("Employee not found with id: " + id);
                });
        return mapToResponseDTO(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRegistrationDTO details) {
        log.info("Updating details for employee ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed: Employee ID {} not found", id);
                    return new EmployeeNotFoundException("Employee not found with id: " + id);
                });

        employee.setEmployeeName(details.getEmployeeName());
        employee.setEmployeeAddress(details.getEmployeeAddress());
        employee.setEmployeeContact(details.getEmployeeContact());
        employee.setEmployeeDepartmentName(details.getEmployeeDepartmentName());

        Employee updated = employeeRepository.save(employee);
        log.info("Employee details updated successfully for ID: {}", id);
        return mapToResponseDTO(updated);
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        log.info("Password change requested for employee ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee ID: " + id + " not found"));

        if (passwordEncoder.matches(oldPassword, employee.getPassword())) {
            employee.setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(employee);
            log.info("Password changed successfully for employee ID: {}", id);
            return true;
        }
        log.warn("Password change failed: Old password mismatch for ID: {}", id);
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



