package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.UserPublicDTO;
import com.cts.SafeWork.dto.UserUpdateDTO;
import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.exception.CustomException;
import com.cts.SafeWork.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
            throw new CustomException("User with this email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public UserPublicDTO updateUser(Long userId, UserUpdateDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found with id: " + userId));

        if (dto.getUserName() != null) {
            user.setUserName(dto.getUserName());
        }
        if (dto.getUserEmail() != null) {
            user.setUserEmail(dto.getUserEmail());
        }
        if (dto.getUserContact() != null) {
            user.setUserContact(dto.getUserContact());
        }
        if (dto.getUserStatus() != null) {
            user.setUserStatus(dto.getUserStatus());
        }
        if (dto.getUserRole() != null) {
            user.setUserRole(dto.getUserRole());
        }

        userRepository.save(user);

        UserPublicDTO res = new UserPublicDTO();
        res.setUserName(user.getUserName());
        res.setUserEmail(user.getUserEmail());
        res.setUserContact(user.getUserContact());
        res.setUserStatus(user.getUserStatus());
        res.setUserRole(user.getUserRole());

        return res;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found with id: " + userId));

        if (user.getIncidents() != null && !user.getIncidents().isEmpty()) {
            throw new CustomException("Cannot delete user. Incidents are assigned to this user");
        }

        userRepository.delete(user);
    }


    @Override
    public UserPublicDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found with id: " + userId));
        return toDTO(user);
    }

    @Override
    public List<UserPublicDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserPublicDTO getUserByEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found with email: " + userEmail));
        return toDTO(user);
    }

    @Override
    public UserPublicDTO getUserByName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException("User not found with name: " + userName));
        return toDTO(user);
    }

    private UserPublicDTO toDTO(User user) {
        UserPublicDTO dto = new UserPublicDTO();

        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserContact(user.getUserContact());
        dto.setUserStatus(user.getUserStatus());
        dto.setUserRole(user.getUserRole());
        return dto;
    }
}