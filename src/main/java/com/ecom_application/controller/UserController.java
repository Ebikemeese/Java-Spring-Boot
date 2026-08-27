package com.ecom_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom_application.model.User;
import com.ecom_application.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
        // return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
            // User user = userService.fetchUserById(id);
            // if (user == null) {
            //     return ResponseEntity.notFound().build();
            // }
            // return ResponseEntity.ok(user);

            return userService.fetchUserById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

    @PostMapping("/api/users")
    public ResponseEntity<String> creatUsers(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
        // return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody User updatedUser) {
        boolean updated = userService.updatedUser(id, updatedUser);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
            // return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
            // Alternative standard REST response (HTTP 204 No Content):
            // return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    
}
