package com.example.postgresql.service.Users;

import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.User.UserRepository;
import com.example.postgresql.repository.Users.User.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    public boolean authenticate(User user, String password) {
        byte[] hashedPassword = hashPassword(password, user.getSalt());
        return Arrays.equals(Arrays.toString(hashedPassword).toCharArray(), Arrays.toString(user.getHash()).toCharArray());
    }

    public byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }

    public byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    public User findUserByLogin(String login){ return userRepository.findUserByLogin(login); }
    public User findUserById(Long id){ return userRepository.findById(id).orElse(null); }

    public void saveUser(User user) {userRepository.save(user);}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserType findUserTypeById(Long id) {
        return userTypeRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id){userRepository.deleteById(id);}

}