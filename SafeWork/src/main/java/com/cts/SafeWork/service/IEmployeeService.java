


package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Employee;
import java.util.Optional;

public interface IEmployeeService {
    Employee registerEmployee(Employee employee);
    Optional<Employee> loginEmployee(String email, String password);

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employeeDetails);

    boolean changePassword(Long id, String oldPassword, String newPassword);
}