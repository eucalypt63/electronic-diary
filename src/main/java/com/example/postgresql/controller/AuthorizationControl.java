package com.example.postgresql.controller;

import com.example.postgresql.DTO.ResponseDTO.AuthorizationUserResponseDTO;
import com.example.postgresql.model.Users.Administrations.Administrations;
import com.example.postgresql.model.Users.LocalOperator;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class AuthorizationControl {

    @Autowired
    private UserService userService;
    @Autowired
    private AdministrationsService administrationsService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LocalOperatorService localOperatorService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;

    @Autowired
    private DTOService dtoService;

    private static final ConcurrentHashMap<String, FailedAttempt> attempts = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final long BLOCK_TIME_MS = 60 * 60 * 1000;

    // Внутренний класс для хранения данных
    private static class FailedAttempt {
        int count;
        long blockTime;

        FailedAttempt(int count, long blockTime) {
            this.count = count;
            this.blockTime = blockTime;
        }
    }

    // Проверка блокировки IP
    private boolean isBlocked(String ip) {
        FailedAttempt attempt = attempts.get(ip);
        if (attempt == null || attempt.count < MAX_ATTEMPTS) return false;

        if (System.currentTimeMillis() < attempt.blockTime) {
            return true;
        } else {
            attempts.remove(ip);
            return false;
        }
    }

    // Обработчик неудачных попыток
    private void handleFailedAttempt(String ip) {
        attempts.compute(ip, (key, value) -> {
            long currentTime = System.currentTimeMillis();
            if (value == null) {
                return new FailedAttempt(1, currentTime + BLOCK_TIME_MS);
            } else if (currentTime >= value.blockTime) {
                return new FailedAttempt(1, currentTime + BLOCK_TIME_MS);
            } else {
                return new FailedAttempt(value.count + 1, value.count + 1 >= MAX_ATTEMPTS ?
                        currentTime + BLOCK_TIME_MS : value.blockTime);
            }
        });
    }

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
        String ip = getClientIP(request);

        // Проверка блокировки
        if (isBlocked(ip)) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body("Превышено количество попыток. Попробуйте через 1 час");
        }

        User user = userService.findUserByLogin(login);

        if (user != null && userService.authenticate(user, password)) {
            attempts.remove(ip);
            session.invalidate();
            session = request.getSession(true);

            UserType userType = user.getUserType();
            switch (userType.getName()) {
                case "Main admin" -> session.setAttribute("userId", user.getId());
                case "Local admin" -> {
                    LocalOperator localOperator = localOperatorService.findLocalOperatorByUserId(user.getId());
                    session.setAttribute("userId", localOperator.getUser().getId());
                    session.setAttribute("id", localOperator.getId());
                }
                case "Administration" -> {
                    Administrations administrations = administrationsService.findAdministrationByUserId(user.getId());
                    session.setAttribute("userId", administrations.getUser().getId());
                    session.setAttribute("id", administrations.getId());
                }
                case "Teacher" -> {
                    Teacher teacher = teacherService.findTeacherByUserId(user.getId());
                    session.setAttribute("userId", teacher.getUser().getId());
                    session.setAttribute("id", teacher.getId());
                }
                case "School student" -> {
                    SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentByUserId(user.getId());
                    session.setAttribute("userId", schoolStudent.getUser().getId());
                    session.setAttribute("id", schoolStudent.getId());
                }
                case "Parent" -> {
                    Parent parent = parentService.findParentByUserId(user.getId());
                    session.setAttribute("userId", parent.getUser().getId());
                    session.setAttribute("id", parent.getId());
                }
            }
            session.setAttribute("role", userType.getName());
            return ResponseEntity.ok(userType.getName());
        }

        handleFailedAttempt(ip);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Неверный логин или пароль");
    }

    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
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
        Long userId = (Long) session.getAttribute("userId");
        Long id = (Long) session.getAttribute("id");

        authorizationUserResponseDTO.setId(id);
        authorizationUserResponseDTO.setUserId(userId);
        authorizationUserResponseDTO.setRole(role);

        return ResponseEntity.ok(authorizationUserResponseDTO);
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

    @GetMapping("/regL")
    public String postRegistrationL(Model model) {
        byte[] salt = {52, 102, 53, 103, 54, 104, 55, 106, 56, 107, 57, 108, 48, 109, 49, 110};
        byte[] hashedPassword = userService.hashPassword("koroley", salt);

        User newUser = new User("localAdmin11", hashedPassword, salt);// 11 12 32 / 85 86 101
        newUser.setUserType(userService.findUserTypeById(2L));
        userService.saveUser(newUser);

        LocalOperator localOperator = new LocalOperator();
        localOperator.setUser(newUser);
        localOperator.setEducationalInstitution(educationalInstitutionService.findEducationalInstitutionById(85L));
        localOperator.setEmail("korol634921777@gmail.com");
        localOperatorService.saveLocalOperator(localOperator);

        //

        User newUser2 = new User("localAdmin12", hashedPassword, salt);
        newUser2.setUserType(userService.findUserTypeById(2L));
        userService.saveUser(newUser2);

        LocalOperator localOperator2 = new LocalOperator();
        localOperator2.setUser(newUser2);
        localOperator2.setEducationalInstitution(educationalInstitutionService.findEducationalInstitutionById(86L));
        localOperator2.setEmail("korol634921777@gmail.com");
        localOperatorService.saveLocalOperator(localOperator2);

        //

        User newUser3 = new User("localAdmin32", hashedPassword, salt);
        newUser3.setUserType(userService.findUserTypeById(2L));
        userService.saveUser(newUser3);

        LocalOperator localOperator3 = new LocalOperator();
        localOperator3.setUser(newUser3);
        localOperator3.setEducationalInstitution(educationalInstitutionService.findEducationalInstitutionById(101L));
        localOperator3.setEmail("korol634921777@gmail.com");
        localOperatorService.saveLocalOperator(localOperator3);

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