package com.example.postgresql.service;

import com.example.postgresql.model.Admin;
import com.example.postgresql.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin createAdmin(String login, String password, String role) {
        Admin admin = new Admin(login, password, role);
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
