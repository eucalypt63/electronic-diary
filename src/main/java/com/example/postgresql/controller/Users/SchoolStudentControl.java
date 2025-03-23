package com.example.postgresql.controller.Users;


import com.example.postgresql.DTO.RequestDTO.SchoolStudentRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.SchoolStudentResponseDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SchoolStudentControl {
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private DTOService dtoService;

    //Получить учеников по id школы
    @GetMapping("/getSchoolStudents")
    @ResponseBody
    public ResponseEntity<List<SchoolStudentResponseDTO>> getSchoolStudents(@RequestParam Long schoolId) {
        List<SchoolStudent> schoolStudents = schoolStudentService.findSchoolStudentByEducationalInstitutionId(schoolId);

        List<SchoolStudentResponseDTO> schoolStudentResponseDTOS = new ArrayList<>();
        for (SchoolStudent schoolStudent : schoolStudents) {
            SchoolStudentResponseDTO schoolStudentResponseDTO = dtoService.SchoolStudentToDto(schoolStudent);
            schoolStudentResponseDTOS.add(schoolStudentResponseDTO);
        }

        if (schoolStudentResponseDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(schoolStudentResponseDTOS);
    }

    //Получить ученика по id
    @GetMapping("/findSchoolStudentById")
    @ResponseBody
    public ResponseEntity<SchoolStudentResponseDTO> findSchoolStudentById(@RequestParam Long id) {
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);
        SchoolStudentResponseDTO schoolStudentResponseDTO = dtoService.SchoolStudentToDto(schoolStudent);

        if (schoolStudentResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(schoolStudentResponseDTO);
    }

    //Получить школу по id ученика
    @GetMapping("/findSchoolBySchoolStudentId")
    @ResponseBody
    public ResponseEntity<EducationalInstitution> findSchoolBySchoolStudentId(@RequestParam Long id) {
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);
        EducationalInstitution educationalInstitution = schoolStudent.getEducationalInstitution();

        return ResponseEntity.ok(educationalInstitution);
    }

    //Добавить ученика
    @PostMapping("/addSchoolStudent")
    @ResponseBody
    public ResponseEntity<String> addSchoolStudents(@RequestBody SchoolStudentRequestDTO schoolStudentRequestDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(schoolStudentRequestDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(4L);
        User user = new User(schoolStudentRequestDTO.getLogin(), hash, salt, userType);
        userService.saveUser(user);

        Class cl = classService.findClassById(schoolStudentRequestDTO.getClassRoomId());
        SchoolStudent schoolStudent = new SchoolStudent(cl, schoolStudentRequestDTO.getFirstName(), schoolStudentRequestDTO.getLastName());
        schoolStudent.setPatronymic(schoolStudentRequestDTO.getPatronymic());
        schoolStudent.setEmail(schoolStudentRequestDTO.getEmail());
        schoolStudent.setPhoneNumber(schoolStudentRequestDTO.getPhoneNumber());
        schoolStudent.setUser(user);
        schoolStudent.setEducationalInstitution(cl.getTeacher().getEducationalInstitution());
        schoolStudentService.saveSchoolStudent(schoolStudent);

        return ResponseEntity.ok("{\"message\": \"Ученик успешно добавлен\"}");
    }

    //Получить учеников по id класса
    @GetMapping("/getStudentsOfClass")
    @ResponseBody
    public ResponseEntity<List<SchoolStudentResponseDTO>> getStudentsOfClass(@RequestParam Long ObjectId) {
        List<SchoolStudent> schoolStudents = schoolStudentService.getAllSchoolStudentByClassId(ObjectId);

        List<SchoolStudentResponseDTO> schoolStudentResponseDTOS = new ArrayList<>();
        for (SchoolStudent schoolStudent : schoolStudents) {
            SchoolStudentResponseDTO schoolStudentResponseDTO = dtoService.SchoolStudentToDto(schoolStudent);
            schoolStudentResponseDTOS.add(schoolStudentResponseDTO);
        }

        if (schoolStudentResponseDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(schoolStudentResponseDTOS);
    }

    //Удалить ученика
    @DeleteMapping("/deleteSchoolStudent")
    public ResponseEntity<Void> deleteSchoolStudent(@RequestParam Long id) {
        schoolStudentService.deleteSchoolStudentById(id);
        return ResponseEntity.ok().build();
    }
}
