package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom query to find employee by email for login functionality
    Optional<Employee> findByEmail(String email);
}