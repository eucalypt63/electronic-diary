package com.example.postgresql.controller;

import com.example.postgresql.model.User;
import com.example.postgresql.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class AuthorizationControl {

    private byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String getAuthorization(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String postAuthorization(@ModelAttribute("login") String login,
                                    @ModelAttribute("password") String password,
                                    Model model) {
        User user = loginService.findUserByLogin(login);
        if (user != null && loginService.authenticate(user, password)) {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(new UsernamePasswordToken(login, password, true));
            return "redirect:/home";
        }
        model.addAttribute("error", "Неверный логин или пароль");
        return "login";
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return "redirect:/login";
    }

}