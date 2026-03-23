package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import java.util.Optional;

public interface IEmployeeService {
    // Registration will take DTO and will give ResponseDTO
    EmployeeResponseDTO registerEmployee(EmployeeRegistrationDTO registrationDTO);

    // Login will take LoginRequestDTO from here
    EmployeeResponseDTO loginEmployee(LoginRequestDTO loginRequest);

    // will get resonseDto by Id(No password)
    EmployeeResponseDTO getEmployeeById(Long id);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeRegistrationDTO details);

    boolean changePassword(Long id, String oldPassword, String newPassword);
}