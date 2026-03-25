package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.UserPublicDTO;
import com.cts.SafeWork.dto.UserUpdateDTO;
import com.cts.SafeWork.entity.User;

import java.util.List;

public interface IUserService {
    // Existing methods...
    UserPublicDTO loginUser(String email, String password);
    User registerUser(User user);
    User createUser(User user);
    UserPublicDTO updateUser(Long userId, UserUpdateDTO dto);
    void deleteUser(Long userId);
    UserPublicDTO getUserById(Long userId);
    List<UserPublicDTO> getAllUsers();
    UserPublicDTO getUserByEmail(String userEmail);
    UserPublicDTO getUserByName(String userName);
}
