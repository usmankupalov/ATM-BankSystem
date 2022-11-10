package com.example.atmbanksystem.service;

import com.example.atmbanksystem.entity.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    List<User> getAllUsers();
}
