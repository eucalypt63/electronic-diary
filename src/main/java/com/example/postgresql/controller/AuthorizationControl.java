package com.example.postgresql.controller;

import com.example.postgresql.model.User;
import com.example.postgresql.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthorizationControl {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String getAuthorization(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String postAuthorization(@ModelAttribute("login") String login,
                                    @ModelAttribute("password") String password,
                                    Model model) {

        User user = authService.findUserByLogin(login);
        System.out.println(user);//
        if (user != null)
        {

        }
        else
        {
            model.addAttribute("error", "Неверный логин или пароль");
        }

        return "login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return "redirect:/login";
    }

}