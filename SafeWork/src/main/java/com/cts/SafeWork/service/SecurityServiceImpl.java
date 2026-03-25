package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.repository.EmployeeRepository;
import com.cts.SafeWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Try Employee
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if (employee.isPresent()) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(employee.get().getEmail())
                    .password(employee.get().getPassword())
                    .authorities("ROLE_EMPLOYEE") // Use authorities for explicit control
                    .build();
        }

        // 2. Try User
        Optional<User> user = userRepository.findByUserEmail(email);
        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.get().getUserEmail())
                    .password(user.get().getPassword())
                    .authorities("ROLE_USER") // Matches .hasRole("USER")
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}