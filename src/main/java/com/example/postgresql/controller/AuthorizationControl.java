package com.example.postgresql.controller;

import com.example.postgresql.model.Admin;
import com.example.postgresql.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthorizationControl {

    @Autowired
    private AdminService adminService;

    //временно
    @GetMapping("/createAdmin")
    public String createAdmin(Model model) {
        Admin newAdmin = new Admin("log", "pass", "admin");
        adminService.createAdmin(newAdmin.getLogin(), newAdmin.getPassword(), newAdmin.getRole());
        System.out.println("Администратор успешно создан");
        return "login";
    }

    //временно
    @GetMapping("/getAllAdmins")
    public String getAllAdmins(Model model) {
        List<Admin> admins = adminService.getAllAdmins();

        for (Admin admin : admins) {
            System.out.println("Администратор: " + admin.getLogin() + ", Роль: " + admin.getRole());
        }

        return "login";
    }

    @GetMapping("/login")
    public String getAuthorization(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}