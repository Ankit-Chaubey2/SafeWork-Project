////package com.cts.SafeWork.service;
////import com.cts.SafeWork.entity.Employee;
////import com.cts.SafeWork.enums.EmployeeStatus;
////import com.cts.SafeWork.repository.EmployeeRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.stereotype.Service;
////
////import java.util.Optional;
////
////@Service
////public class EmployeeServiceImpl implements IEmployeeService {
////
////    @Autowired
////    private EmployeeRepository employeeRepository;
////
////    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
////
////    @Override
////    public Employee registerEmployee(Employee employee) {
////        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
////        // Securely hash the password before saving
////        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
////        return employeeRepository.save(employee);
////    }
////
////    @Override
////    public Optional<Employee> loginEmployee(String email, String password) {
////        return employeeRepository.findByEmail(email)
////                .filter(emp -> passwordEncoder.matches(password, emp.getPassword()));
////    }
////
////    @Override
////    public Optional<Employee> getEmployeeById(Long id) {
////        return employeeRepository.findById(id);
////    }
////
////    @Override
////    public Employee updateEmployee(Long id, Employee details) {
////        return employeeRepository.findById(id).map(employee -> {
////            employee.setEmployeeName(details.getEmployeeName());
////            employee.setEmployeeAddress(details.getEmployeeAddress());
////            employee.setEmployeeContact(details.getEmployeeContact());
////            employee.setEmployeeDepartmentName(details.getEmployeeDepartmentName());
////            employee.setEmployeeStatus(details.getEmployeeStatus());
////            return employeeRepository.save(employee);
////        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
////    }
////
////    @Override
////    public boolean changePassword(Long id, String oldPassword, String newPassword) {
////        Employee employee = employeeRepository.findById(id).orElse(null);
////        if (employee != null) {
////            // Kyunki hum BCrypt use kar rahe hain, matches() function use karna hoga
////            if (passwordEncoder.matches(oldPassword, employee.getPassword())) {
////                employee.setPassword(passwordEncoder.encode(newPassword));
////                employeeRepository.save(employee);
////                return true;
////            }
////        }
////        return false;
////    }
////}
//
//package com.cts.SafeWork.service;
//
//import com.cts.SafeWork.entity.Employee;
//import com.cts.SafeWork.enums.EmployeeStatus;
//import com.cts.SafeWork.exception.EmployeeNotFoundException; // Naya import
//import com.cts.SafeWork.repository.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class EmployeeServiceImpl implements IEmployeeService {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Override
//    public Employee registerEmployee(Employee employee) {
//        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
//        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
//        return employeeRepository.save(employee);
//    }
//
//    @Override
//    public Optional<Employee> loginEmployee(String email, String password) {
//        return employeeRepository.findByEmail(email)
//                .filter(emp -> passwordEncoder.matches(password, emp.getPassword()));
//    }
//
//    // 1. Updated: Get Employee (Throws Exception if not found)
//    @Override
//    public Optional<Employee> getEmployeeById(Long id) {
//        // findById khud Optional deta hai, orElseThrow use tabhi karo jab aapko direct Employee chahiye ho.
//        // Lekin humein Optional return karna hai, toh hum aise likhenge:
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
//        return Optional.of(employee);
//    }
//
//    // 2. Updated: Update Employee (Custom Exception use kiya instead of RuntimeException)
//    @Override
//    public Employee updateEmployee(Long id, Employee details) {
//        return employeeRepository.findById(id).map(employee -> {
//            employee.setEmployeeName(details.getEmployeeName());
//            employee.setEmployeeAddress(details.getEmployeeAddress());
//            employee.setEmployeeContact(details.getEmployeeContact());
//            employee.setEmployeeDepartmentName(details.getEmployeeDepartmentName());
//            employee.setEmployeeStatus(details.getEmployeeStatus());
//            return employeeRepository.save(employee);
//        }).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
//    }
//
//    // 3. Updated: Change Password (Yahan bhi hum Exception throw kar sakte hain)
//    @Override
//    public boolean changePassword(Long id, String oldPassword, String newPassword) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
//
//        if (passwordEncoder.matches(oldPassword, employee.getPassword())) {
//            employee.setPassword(passwordEncoder.encode(newPassword));
//            employeeRepository.save(employee);
//            return true;
//        }
//        return false;
//    }
//}


package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.enums.EmployeeStatus;
import com.cts.SafeWork.exception.EmployeeNotFoundException;
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
    public EmployeeResponseDTO registerEmployee(EmployeeRegistrationDTO dto) {
        Employee employee = new Employee();
        // Mapping: DTO to Entity
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

    @Override
    public EmployeeResponseDTO loginEmployee(LoginRequestDTO loginRequest) {
        Employee employee = employeeRepository.findByEmail(loginRequest.getEmail())
                .filter(emp -> passwordEncoder.matches(loginRequest.getPassword(), emp.getPassword()))
                .orElseThrow(() -> new EmployeeNotFoundException("Invalid email or password!"));

        return mapToResponseDTO(employee);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return mapToResponseDTO(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRegistrationDTO details) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        employee.setEmployeeName(details.getEmployeeName());
        employee.setEmployeeAddress(details.getEmployeeAddress());
        employee.setEmployeeContact(details.getEmployeeContact());
        employee.setEmployeeDepartmentName(details.getEmployeeDepartmentName());

        Employee updated = employeeRepository.save(employee);
        return mapToResponseDTO(updated);
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        if (passwordEncoder.matches(oldPassword, employee.getPassword())) {
            employee.setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    // Helper Method: Entity ko DTO mein convert karne ke liye
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
