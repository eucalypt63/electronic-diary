package com.example.postgresql.service;

import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.Education.EducationalInstitutionRepository;
import com.example.postgresql.repository.Users.User.UserRepository;
import com.example.postgresql.repository.Users.User.UserTypeRepository;
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

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    public boolean authenticate(User user, String password) {
        byte[] hashedPassword = hashPassword(password, user.getSalt());
        return Arrays.equals(Arrays.toString(hashedPassword).toCharArray(), Arrays.toString(user.getHash()).toCharArray());
    }

    public  byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }


    public User findUserByLogin(String login){ return userRepository.findUserByLogin(login); }

    public UserType findUserTypeById(Long id){ return userTypeRepository.findById(id).orElse(null); }

    //Временно
    public void saveUser(User user) {userRepository.save(user);}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Administrator> getAllAdministrators(){ return administratorRepository.findAll(); }

}