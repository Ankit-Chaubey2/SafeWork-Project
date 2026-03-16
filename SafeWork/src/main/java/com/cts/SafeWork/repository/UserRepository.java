package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
