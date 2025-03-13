package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.TeacherDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Users.TeacherService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TeacherControl {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;

    @Autowired
    private ClassService classService;

    //Получить учителей по id школы
    @GetMapping("/getTeachers")
    @ResponseBody
    public ResponseEntity<List<Teacher>> getTeachers(@RequestParam Long schoolId) {
        List<Teacher> teachers = teacherService.findTeacherByEducationalInstitutionId(schoolId);

        if (teachers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(teachers);
        }

        return ResponseEntity.ok(teachers);
    }

    //Получить учителя по id
    @GetMapping("/findTeacherById")
    @ResponseBody
    public ResponseEntity<Teacher> findTeacherById(@RequestParam Long id) {
        Teacher teacher = teacherService.findTeacherById(id);

        if (teacher == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(teacher);
    }

    //Получить школу по id учителя
    @GetMapping("/findSchoolByTeacherId")
    @ResponseBody
    public ResponseEntity<EducationalInstitution> findSchoolByTeacherId(@RequestParam Long id) {
        Teacher teacher = teacherService.findTeacherById(id);
        EducationalInstitution educationalInstitution = teacher.getEducationalInstitution();

        return ResponseEntity.ok(educationalInstitution);
    }

    //Получить учителей исключив тех, у кого уже есть свой класс
    @GetMapping("/getTeachersToClass")
    @ResponseBody
    public ResponseEntity<List<Teacher>> getTeachersToClass(@RequestParam Long schoolId) {
        List<Class> classes = classService.getAllClasses();
        Set<Long> assignedTeacherIds = classes.stream()
                .map(classEntity -> classEntity.getTeacher().getId())
                .collect(Collectors.toSet());

        List<Teacher> teachers = teacherService.getTeachersBySchoolId(schoolId, new ArrayList<>(assignedTeacherIds));

        if (teachers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(teachers);
    }

    //Добавить учителя
    @PostMapping("/addTeacher")
    @ResponseBody
    public ResponseEntity<String> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(teacherDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(3L);
        EducationalInstitution educationalInstitution = educationalInstitutionService.
                getEducationalInstitutionById(teacherDTO.getUniversityId());
        User user = new User(teacherDTO.getLogin(), hash, salt, userType);
        userService.saveUser(user);

        Teacher teacher = new Teacher(teacherDTO.getFirstName(), teacherDTO.getLastName());
        teacher.setPatronymic(teacherDTO.getPatronymic());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setUser(user);
        teacher.setEducationalInstitution(educationalInstitution);
        teacherService.saveTeacher(teacher);

        return ResponseEntity.ok("{\"message\": \"Учитель успешно добавлен\"}");
    }

    //Удалить учителя
    @DeleteMapping("/deleteTeacher")
    public ResponseEntity<Void> deleteTeacher(@RequestParam("id") Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.ok().build();
    }
}
