package com.example.postgresql.service;

import com.example.postgresql.model.User;
import com.example.postgresql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(User user, String password) {
        byte[] hashedPassword = hashPassword(password, user.getSalt());
        return Arrays.equals(hashedPassword, user.getHash());
    }

    private byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }


    public User findUserByLogin(String login){ return userRepository.findUserByLogin(login); }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}