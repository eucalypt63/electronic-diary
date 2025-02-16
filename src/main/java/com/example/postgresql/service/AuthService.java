package com.example.postgresql.service;

import com.example.postgresql.model.User;
import com.example.postgresql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByLogin(String login){ return userRepository.findUserByLogin(login); }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
