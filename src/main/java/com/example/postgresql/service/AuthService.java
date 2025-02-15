package com.example.postgresql.service;

import com.example.postgresql.model.Admin;
import com.example.postgresql.model.ScAdmin;
import com.example.postgresql.repository.AdminRepository;
import com.example.postgresql.repository.ScAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ScAdminRepository scAdminRepository;

    public Admin checkAuthAdmin(String login, String password) {
        Admin admin = adminRepository.findByLogin(login);

        if (admin != null && admin.getPassword().equals(password)) {//шифровка
            return admin;
        }
        return null;
    }

    public ScAdmin checkAuthScAdmin(String login, String password) {
        ScAdmin scAdmin = scAdminRepository.findByLogin(login);

        if (scAdmin != null && scAdmin.getPassword().equals(password)) {//шифровка
            return scAdmin;
        }
        return null;
    }


    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
