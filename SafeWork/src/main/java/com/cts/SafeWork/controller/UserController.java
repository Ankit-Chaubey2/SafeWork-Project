package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.UserPublicDTO;
import com.cts.SafeWork.dto.UserUpdateDTO;
import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PatchMapping("updateUser/{userId}")
    public ResponseEntity<UserPublicDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateDTO dto) {

        return ResponseEntity.ok(userService.updateUser(userId, dto));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserPublicDTO> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserPublicDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getByEmail/{userEmail}")
    public ResponseEntity<UserPublicDTO> getUserByEmail(@PathVariable String userEmail) {
        return ResponseEntity.ok(userService.getUserByEmail(userEmail));
    }

    @GetMapping("/getByName/{userName}")
    public ResponseEntity<UserPublicDTO> getUserByName(@PathVariable String userName) {
        return ResponseEntity.ok(userService.getUserByName(userName));
    }
}