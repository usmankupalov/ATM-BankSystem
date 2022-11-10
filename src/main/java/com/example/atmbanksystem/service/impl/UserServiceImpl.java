package com.example.atmbanksystem.service.impl;

import com.example.atmbanksystem.entity.User;
import com.example.atmbanksystem.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public void createUser(User user) {
        Transaction.of(session -> {
            session.save(user);
            return user;
        }).run();
    }

    @Override
    public List<User> getAllUsers() {
        return Transaction.of(session -> {
            List<User> users = session.createQuery(
                    "select u " +
                            "from User u ", User.class)
                    .getResultList();
            return users;
        }).run();
    }
}
