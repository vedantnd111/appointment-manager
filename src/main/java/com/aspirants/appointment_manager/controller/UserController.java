package com.aspirants.appointment_manager.controller;

import com.aspirants.appointment_manager.dto.UserProfileRequest;
import com.aspirants.appointment_manager.dto.UserProfileResponse;
import com.aspirants.appointment_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user
    @PostMapping("/create")
    public ResponseEntity<UserProfileResponse> createUser(@Valid @RequestBody UserProfileRequest request) {
        UserProfileResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        UserProfileResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<UserProfileResponse> responses = userService.getAllUsers();
        return ResponseEntity.ok(responses);
    }

    // Update user by ID (full update)
    @PutMapping("/update/{id}")
    public ResponseEntity<UserProfileResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileRequest request) {
        UserProfileResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    // Partial update user by ID (optional, best practice)
    @PatchMapping("/update/{id}")
    public ResponseEntity<UserProfileResponse> partialUpdateUser(
            @PathVariable Long id,
            @RequestBody UserProfileRequest request) {
        UserProfileResponse response = userService.updateUser(id, request); // Implement partial update in service if needed
        return ResponseEntity.ok(response);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
