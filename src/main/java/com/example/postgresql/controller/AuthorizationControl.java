package com.example.postgresql.controller;

import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthorizationControl {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getAuthorization(Model model) {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> postAuthorization(@RequestParam("login") String login,
                                                    @RequestParam("password") String password,
                                                    HttpSession session,
                                                    HttpServletRequest request) {
        User user = userService.findUserByLogin(login);

        if (user != null && userService.authenticate(user, password)) {
            session.invalidate();
            session = request.getSession(true);

            UserType userType = user.getUserType();
            if (userType.getName().equals("Main admin")) {
                session.setAttribute("user", user);
            } else if (userType.getName().equals("Local admin"))
            {
                List<Administrator> administrators = userService.getAllAdministrators()
                        .stream()
                        .filter(admin -> admin.getUser().equals(user))
                        .toList();
                Administrator administrator = administrators.get(0);
                session.setAttribute("user", administrator);
            }
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
        if ("Main admin".equals(role) || "Local admin".equals(role)) {
            return "adminSettings";
        }
        System.out.println("Неверная роль");
        return "redirect:/login";
    }


    //временно
    @GetMapping("/reg")
    public String postRegistration(Model model) {
        byte[] salt = {52, 102, 53, 103, 54, 104, 55, 106, 56, 107, 57, 108, 48, 109, 49, 110};
        byte[] hashedPassword = userService.hashPassword("koroley", salt);

        User newUser = new User("admin", hashedPassword, salt, userService.findUserTypeById(1L));
        userService.saveUser(newUser);

        model.addAttribute("success", "Пользователь успешно зарегистрирован");
        return "login";
    }
}