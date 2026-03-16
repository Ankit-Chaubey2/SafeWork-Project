package com.cts.SafeWork.service;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.enums.EmployeeStatus;
import com.cts.SafeWork.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Employee registerEmployee(Employee employee) {
        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
        // Securely hash the password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> loginEmployee(String email, String password) {
        return employeeRepository.findByEmail(email)
                .filter(emp -> passwordEncoder.matches(password, emp.getPassword()));
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Long id, Employee details) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setEmployeeName(details.getEmployeeName());
            employee.setEmployeeAddress(details.getEmployeeAddress());
            employee.setEmployeeContact(details.getEmployeeContact());
            employee.setEmployeeDepartmentName(details.getEmployeeDepartmentName());
            employee.setEmployeeStatus(details.getEmployeeStatus());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            // Kyunki hum BCrypt use kar rahe hain, matches() function use karna hoga
            if (passwordEncoder.matches(oldPassword, employee.getPassword())) {
                employee.setPassword(passwordEncoder.encode(newPassword));
                employeeRepository.save(employee);
                return true;
            }
        }
        return false;
    }
}