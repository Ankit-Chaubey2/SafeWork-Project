package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    // 1. Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.registerEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // 2. Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Optional<Employee> employee = employeeService.loginEmployee(email, password);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get()); // Returns the full employee object on success
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // 3. Get Employee by ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}") // EmployeeRegistrationDto
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee details) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, details);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/logout/{id}")
    public ResponseEntity<String> logout(@PathVariable Long id) {
        // Yahan aap console par print kar sakte ho check karne ke liye
        System.out.println("User with ID " + id + " is logging out...");

        // Future mein yahan 'Last Login/Logout' time update kar sakte ho DB mein
        return ResponseEntity.ok("Logout successful for Employee ID: " + id);
    }


    //to see the profile of employee on employee dashboard
    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getEmployeeStats(@PathVariable Long id) {
        Optional<Employee> emp = employeeService.getEmployeeById(id);
        if(emp.isPresent()) {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalDocuments", emp.get().getDocuments().size());
            stats.put("accountStatus", emp.get().getEmployeeStatus());
            stats.put("department", emp.get().getEmployeeDepartmentName());
            return ResponseEntity.ok(stats);
        }
        return ResponseEntity.notFound().build();
    }



    //change password logic
    @PatchMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");

        boolean isChanged = employeeService.changePassword(id, oldPassword, newPassword);
        if (isChanged) {
            return ResponseEntity.ok("Password updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect!");
        }
    }

}