package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import java.util.Optional;

public interface IEmployeeService {

    EmployeeResponseDTO registerEmployee(EmployeeRegistrationDTO registrationDTO);


    EmployeeResponseDTO loginEmployee(LoginRequestDTO loginRequest);


    EmployeeResponseDTO getEmployeeById(Long id);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeRegistrationDTO details);

    boolean changePassword(Long id, String oldPassword, String newPassword);
}