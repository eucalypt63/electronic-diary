package com.example.postgresql.controller;

import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthorizationControl {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String getAuthorization(Model model) {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> postAuthorization(@RequestParam("login") String login,
                                                    @RequestParam("password") String password,
                                                    HttpSession session) {
        User user = loginService.findUserByLogin(login);

        if (user != null && loginService.authenticate(user, password)) {
            UserType userType = user.getUserType();
            session.setAttribute("user", user);
            session.setAttribute("role", userType.getName());

            return ResponseEntity.ok(userType.getName());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Неверный логин или пароль");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }



    @PostMapping("/getRole")
    @ResponseBody
    public ResponseEntity<String> getRole(HttpSession session) {
        return ResponseEntity.ok((String) session.getAttribute("role"));
    }

    @GetMapping("/adminSettings")
    public String adminSettings(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        System.out.println(role);
        if ("Main admin".equals(role)) {
            return "adminSettings";
        }
        System.out.println("Неверная роль");
        return "redirect:/login";
    }


    //временно
    @GetMapping("/reg")
    public String postRegistration(Model model) {
        byte[] salt = generateSalt();
        byte[] hashedPassword = loginService.hashPassword("koroley3", salt);

        User newUser = new User("admin3", hashedPassword, salt, loginService.findUserTypeById(Long.valueOf(1)));
        loginService.saveUser(newUser);

        model.addAttribute("success", "Пользователь успешно зарегистрирован");
        return "login";
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[]{52, 102, 53, 103, 54, 104, 55, 106, 56, 107, 57, 108, 48, 109, 49, 110}; // 16 байт соли
        return salt;
    }

}