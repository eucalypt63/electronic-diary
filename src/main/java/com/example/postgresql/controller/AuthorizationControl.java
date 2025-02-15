package com.example.postgresql.controller;

import com.example.postgresql.model.Admin;
import com.example.postgresql.model.ScAdmin;
import com.example.postgresql.service.AdminService;
import com.example.postgresql.service.AuthService;
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

    @Autowired
    private AuthService authService;

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

    @PostMapping("/login")
    public String postAuthorization(@ModelAttribute("username") String username,
                                    @ModelAttribute("password") String password,
                                    Model model){
        Admin admin = authService.checkAuthAdmin(username, password);
        ScAdmin scAdmin = authService.checkAuthScAdmin(username, password);

        if (admin != null) {
            System.out.println("Администратор: " + admin.getLogin() + ", Роль: " + admin.getRole());
            return "login";//
        }
        else if (scAdmin != null){
            System.out.println("школьный Администратор: " + scAdmin.getLogin() + ", Роль: " + scAdmin.getRole());
            return "login";//
        }
        else {
            System.out.println("Не найден");//
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return "redirect:/login";
    }

}