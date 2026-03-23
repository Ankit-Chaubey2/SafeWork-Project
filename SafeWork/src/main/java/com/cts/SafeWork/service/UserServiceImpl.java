package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.exception.CustomException;
import com.cts.SafeWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws CustomException {
        // Check if user already exists by email
        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
            throw new CustomException("User with this email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws CustomException {
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if (existingUser.isEmpty()) {
            throw new CustomException("User not found with id: " + user.getUserId());
        }
        
        User userToUpdate = existingUser.get();
        userToUpdate.setUserName(user.getUserName());
        userToUpdate.setUserEmail(user.getUserEmail());
        userToUpdate.setUserContact(user.getUserContact());
        userToUpdate.setUserStatus(user.getUserStatus());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUserRole(user.getUserRole());
        
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long userId) throws CustomException {
        if (!userRepository.existsById(userId)) {
            throw new CustomException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
