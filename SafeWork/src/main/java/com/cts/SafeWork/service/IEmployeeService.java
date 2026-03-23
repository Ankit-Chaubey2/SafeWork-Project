//
//
//
//package com.cts.SafeWork.service;
//
//import com.cts.SafeWork.entity.Employee;
//import java.util.Optional;
//
//public interface IEmployeeService {
//    Employee registerEmployee(Employee employee);
//    Optional<Employee> loginEmployee(String email, String password);
//
//    Optional<Employee> getEmployeeById(Long id);
//
//    Employee updateEmployee(Long id, Employee employeeDetails);
//
//    boolean changePassword(Long id, String oldPassword, String newPassword);
//}



package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import java.util.Optional;

public interface IEmployeeService {
    // Registration ab DTO lega aur ResponseDTO dega
    EmployeeResponseDTO registerEmployee(EmployeeRegistrationDTO registrationDTO);

    // Login ab LoginRequestDTO lega
    EmployeeResponseDTO loginEmployee(LoginRequestDTO loginRequest);

    // will get resonseDto by Id(No password)
    EmployeeResponseDTO getEmployeeById(Long id);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeRegistrationDTO details);

    boolean changePassword(Long id, String oldPassword, String newPassword);
}