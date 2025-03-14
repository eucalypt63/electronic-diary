package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.RequestDTO.TeacherRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.TeacherResponseDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
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
    @Autowired
    private DTOService dtoService;

    //Получить учителей по id школы
    @GetMapping("/getTeachers")
    @ResponseBody
    public ResponseEntity<List<TeacherResponseDTO>> getTeachers(@RequestParam Long schoolId) {
        List<Teacher> teachers = teacherService.findTeacherByEducationalInstitutionId(schoolId);

        List<TeacherResponseDTO> teacherResponseDTOS = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherResponseDTO teacherResponseDTO = dtoService.TeacherToDto(teacher);
            teacherResponseDTOS.add(teacherResponseDTO);
        }

        if (teacherResponseDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(teacherResponseDTOS);
    }

    //Получить учителя по id
    @GetMapping("/findTeacherById")
    @ResponseBody
    public ResponseEntity<TeacherResponseDTO> findTeacherById(@RequestParam Long id) {
        Teacher teacher = teacherService.findTeacherById(id);
        TeacherResponseDTO teacherResponseDTO = dtoService.TeacherToDto(teacher);

        if (teacherResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(teacherResponseDTO);
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
    public ResponseEntity<List<TeacherResponseDTO>> getTeachersToClass(@RequestParam Long schoolId) {
        List<Class> classes = classService.getAllClasses();
        Set<Long> assignedTeacherIds = classes.stream()
                .map(classEntity -> classEntity.getTeacher().getId())
                .collect(Collectors.toSet());

        List<Teacher> teachers = teacherService.getTeachersBySchoolId(schoolId, new ArrayList<>(assignedTeacherIds));

        List<TeacherResponseDTO> teacherResponseDTOS = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherResponseDTO teacherResponseDTO = dtoService.TeacherToDto(teacher);
            teacherResponseDTOS.add(teacherResponseDTO);
        }

        if (teacherResponseDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(teacherResponseDTOS);
    }

    //Добавить учителя
    @PostMapping("/addTeacher")
    @ResponseBody
    public ResponseEntity<String> addTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(teacherRequestDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(3L);
        EducationalInstitution educationalInstitution = educationalInstitutionService.
                getEducationalInstitutionById(teacherRequestDTO.getUniversityId());
        User user = new User(teacherRequestDTO.getLogin(), hash, salt, userType);
        userService.saveUser(user);

        Teacher teacher = new Teacher(teacherRequestDTO.getFirstName(), teacherRequestDTO.getLastName());
        teacher.setPatronymic(teacherRequestDTO.getPatronymic());
        teacher.setEmail(teacherRequestDTO.getEmail());
        teacher.setPhoneNumber(teacherRequestDTO.getPhoneNumber());
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
