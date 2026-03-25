package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.EmployeeRegistrationDTO;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.dto.LoginRequestDTO;
import com.cts.SafeWork.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j; // Log import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponseDTO> register(@Valid @RequestBody EmployeeRegistrationDTO registrationDTO) {
        log.info("REST request to register employee: {}", registrationDTO.getEmail());
        return new ResponseEntity<>(employeeService.registerEmployee(registrationDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<EmployeeResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("REST request to login employee: {}", loginRequest.getEmail());
        return ResponseEntity.ok(employeeService.loginEmployee(loginRequest));
    }

    @GetMapping("/getEmployeebyId/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        log.info("REST request to get employee details for ID: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRegistrationDTO details) {
        log.info("REST request to update employee ID: {}", id);
        return ResponseEntity.ok(employeeService.updateEmployee(id, details));
    }

    @PatchMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
        log.info("REST request to change password for employee ID: {}", id);
        boolean isChanged = employeeService.changePassword(id, passwords.get("oldPassword"), passwords.get("newPassword"));
        if (isChanged) {
            return ResponseEntity.ok("Password updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password!");
    }
//
//    @PostMapping("/logout/{id}")
//    public ResponseEntity<String> logout(@PathVariable Long id) {
//        log.info("REST request to logout employee ID: {}", id);
//        return ResponseEntity.ok("Logout successful for Employee ID: " + id);
//    }

//    @GetMapping("/employeestatus/{id}")
//    public ResponseEntity<Map<String, Object>> getEmployeeStats(@PathVariable Long id) {
//        log.info("REST request for stats of employee ID: {}", id);
//        EmployeeResponseDTO emp = employeeService.getEmployeeById(id);
//        Map<String, Object> stats = new HashMap<>();
//        stats.put("accountStatus", emp.getEmployeeStatus());
//        stats.put("department", emp.getEmployeeDepartmentName());
//        return ResponseEntity.ok(stats);
//    }
}