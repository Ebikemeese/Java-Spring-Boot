package com.ecom_application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecom_application.model.User;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> fetchAllUsers() {
        return userList;
    }

    public Optional<User> fetchUserById(Long id) {
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public List<User> addUser(User user) {
        user.setId(nextId++);
        userList.add(user);
        return userList;
    }

    public boolean updatedUser(Long id, User updatedUser) {
        return userList.stream()
        .filter(user -> user.getId().equals(id))
        .findFirst()
        .map(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            return true;
        }).orElse(false);
    }

    public boolean deleteUser(Long id) {
        return userList.removeIf(user -> user.getId().equals(id));
    }
}
