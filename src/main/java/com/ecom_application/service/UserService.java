package com.ecom_application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecom_application.model.User;
import com.ecom_application.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    // private List<User> userList = new ArrayList<>();
    // private Long nextId = 1L;


    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> fetchUserById(Long id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean updatedUser(Long id, User updatedUser) {
        return userRepository.findById(id)
        .map(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
