package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(String username, String password, List<String> authorities);

    Optional<User> findUser(String username);

    void deleteUser(String username);

    void updateAuthorities(String username, List<String> authorities);

    void setUserPassword(String username, String password);
}