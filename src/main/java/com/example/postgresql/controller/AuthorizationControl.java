package com.example.postgresql.controller;

import com.example.postgresql.DTO.ResponseDTO.AuthorizationUserResponseDTO;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.Users.*;
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
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private TeacherService teacherService;

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
            switch (userType.getName()) {
                case "Main admin" -> session.setAttribute("userId", user.getId());
                case "Local admin" -> {
                    Administrator administrator = administratorService.findAdministratorByUserId(user.getId());
                    session.setAttribute("userId", administrator.getUser().getId());
                }
                case "Teacher" -> {
                    Teacher teacher = teacherService.findTeacherByUserId(user.getId());
                    session.setAttribute("userId", teacher.getUser().getId());
                }
                case "School student" -> {
                    SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentByUserId(user.getId());
                    session.setAttribute("userId", schoolStudent.getUser().getId());
                }
                case "Parent" -> {
                    Parent parent = parentService.findParentByUserId(user.getId());
                    session.setAttribute("userId", parent.getUser().getId());
                }
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

    //Получить роль в сессии
    @GetMapping("/getRole")
    @ResponseBody
    public ResponseEntity<String> getRole(HttpSession session) {
        return ResponseEntity.ok((String) session.getAttribute("role"));
    }

    @GetMapping("/getAuthorizationUserInfo")
    @ResponseBody
    public ResponseEntity<AuthorizationUserResponseDTO> getAuthorizationUserInfo(HttpSession session) {
        AuthorizationUserResponseDTO authorizationUserResponseDTO = new AuthorizationUserResponseDTO();

        String role = (String) session.getAttribute("role");
        Long id = (Long) session.getAttribute("userId");

        authorizationUserResponseDTO.setId(id);
        authorizationUserResponseDTO.setRole(role);

        return ResponseEntity.ok(authorizationUserResponseDTO);
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

        User newUser = new User("admin", hashedPassword, salt);
        newUser.setUserType(userService.findUserTypeById(1L));
        userService.saveUser(newUser);

        model.addAttribute("success", "Пользователь успешно зарегистрирован");
        return "login";
    }

    @GetMapping("/UAll")
    @ResponseBody
    public  ResponseEntity<List<User>> GAll(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}