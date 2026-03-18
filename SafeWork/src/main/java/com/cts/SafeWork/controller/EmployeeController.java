//
//
//
//package com.cts.SafeWork.controller;
//
//import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
//import com.cts.SafeWork.dto.EmployeeResponseDTO;
//import com.cts.SafeWork.dto.LoginRequestDTO;
//import com.cts.SafeWork.service.IEmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/employees")
//public class EmployeeController {
//
//    @Autowired
//    private IEmployeeService employeeService;
//
//    // 1. Register: pass the DTO
//    @PostMapping("/register")
//    public ResponseEntity<EmployeeResponseDTO> register(@RequestBody EmployeeRegistrationDTO registrationDTO) {
//        return new ResponseEntity<>(employeeService.registerEmployee(registrationDTO), HttpStatus.CREATED);
//    }
//
//    // 2. Login: used logic in service
//    @PostMapping("/login")
//    public ResponseEntity<EmployeeResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
//        return ResponseEntity.ok(employeeService.loginEmployee(loginRequest));
//    }
//
//    // 3. Get Employee:
//    @GetMapping("/{id}")
//    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
//        return ResponseEntity.ok(employeeService.getEmployeeById(id));
//    }
//
//    // 4. Update Employee:
//    @PutMapping("/{id}")
//    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRegistrationDTO details) {
//        return ResponseEntity.ok(employeeService.updateEmployee(id, details));
//    }
//
//    // 5. Change Password
//    @PostMapping("/{id}/change-password")
//    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
//        boolean isChanged = employeeService.changePassword(id, passwords.get("oldPassword"), passwords.get("newPassword"));
//        if (isChanged) {
//            return ResponseEntity.ok("Password updated successfully!");
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password!");
//    }
//
//    @PostMapping("/logout/{id}")
//    public ResponseEntity<String> logout(@PathVariable Long id) {
//        return ResponseEntity.ok("Logout successful for Employee ID: " + id);
//    }
//
//
//
//    @GetMapping("/{id}/stats")
//    public ResponseEntity<Map<String, Object>> getEmployeeStats(@PathVariable Long id) {
//
//        //take dto from service and throw exception if id is wrong
//        EmployeeResponseDTO emp = employeeService.getEmployeeById(id);
//
//        Map<String, Object> stats = new HashMap<>();
//        stats.put("accountStatus", emp.getEmployeeStatus());
//        stats.put("department", emp.getEmployeeDepartmentName());
//
//
//        return ResponseEntity.ok(stats);
//    }
//}



package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import com.cts.SafeWork.service.IEmployeeService;
import jakarta.validation.Valid; // Zaroori import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    // Added @Valid here
    @PostMapping("/register")
    public ResponseEntity<EmployeeResponseDTO> register(@Valid @RequestBody EmployeeRegistrationDTO registrationDTO) {
        return new ResponseEntity<>(employeeService.registerEmployee(registrationDTO), HttpStatus.CREATED);
    }

    // Login validation (Email/Password check)
    @PostMapping("/login")
    public ResponseEntity<EmployeeResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(employeeService.loginEmployee(loginRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Added @Valid here
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRegistrationDTO details) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, details));
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
        boolean isChanged = employeeService.changePassword(id, passwords.get("oldPassword"), passwords.get("newPassword"));
        if (isChanged) {
            return ResponseEntity.ok("Password updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password!");
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<String> logout(@PathVariable Long id) {
        return ResponseEntity.ok("Logout successful for Employee ID: " + id);
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getEmployeeStats(@PathVariable Long id) {
        EmployeeResponseDTO emp = employeeService.getEmployeeById(id);
        Map<String, Object> stats = new HashMap<>();
        stats.put("accountStatus", emp.getEmployeeStatus());
        stats.put("department", emp.getEmployeeDepartmentName());
        return ResponseEntity.ok(stats);
    }
}