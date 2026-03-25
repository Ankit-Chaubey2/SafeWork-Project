package com.cts.SafeWork.security;

import com.cts.SafeWork.repository.UserRepository;
import com.cts.SafeWork.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Try to find the person in the USER table (Officers/Admin)
        var userEntity = userRepository.findByUserEmail(email);
        if (userEntity.isPresent()) {
            // Get the name from Enum (e.g., "ADMIN" or "HAZARD_OFFICER")
            String roleName = userEntity.get().getUserRole().name();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(userEntity.get().getUserEmail())
                    .password(userEntity.get().getPassword())
                    // IMPORTANT: No "ROLE_" prefix here because your config uses .hasAuthority()
                    .authorities(List.of(new SimpleGrantedAuthority(roleName)))
                    .build();
        }

        // 2. Try to find them in the EMPLOYEE table
        var employeeEntity = employeeRepository.findByEmail(email);
        if (employeeEntity.isPresent()) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(employeeEntity.get().getEmail())
                    .password(employeeEntity.get().getPassword())
                    // Matches .hasAnyAuthority("EMPLOYEE") in SecurityConfig
                    .authorities(List.of(new SimpleGrantedAuthority("EMPLOYEE")))
                    .build();
        }

        throw new UsernameNotFoundException("Access Denied: No User or Employee found with email: " + email);
    }
}
