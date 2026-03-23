package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.exception.CustomException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(User user) throws CustomException;
    User updateUser(User user) throws CustomException;
    void deleteUser(Long userId) throws CustomException;
    Optional<User> getUserById(Long userId);
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String userEmail);
    Optional<User> getUserByName(String userName);
}
