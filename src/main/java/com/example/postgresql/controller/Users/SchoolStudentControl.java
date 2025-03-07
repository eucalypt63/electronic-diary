package com.example.postgresql.controller.Users;


import com.example.postgresql.DTO.SchoolStudentDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SchoolStudentControl {
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;

    @GetMapping("/getSchoolStudents")
    @ResponseBody
    public ResponseEntity<List<SchoolStudent>> getSchoolStudents(@RequestParam Long schoolId) {
        List<SchoolStudent> schoolStudents = schoolStudentService.findSchoolStudentByEducationalInstitutionId(schoolId);

        if (schoolStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(schoolStudents);
        }

        return ResponseEntity.ok(schoolStudents);
    }

    @GetMapping("/findSchoolStudentById")
    @ResponseBody
    public ResponseEntity<SchoolStudent> findSchoolStudentById(@RequestParam Long id) {
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);

        if (schoolStudent == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(schoolStudent);
    }

    @GetMapping("/findSchoolBySchoolStudentId")
    @ResponseBody
    public ResponseEntity<EducationalInstitution> findSchoolBySchoolStudentId(@RequestParam Long id) {
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);
        EducationalInstitution educationalInstitution = schoolStudent.getEducationalInstitution();

        return ResponseEntity.ok(educationalInstitution);
    }

    @PostMapping("/addSchoolStudent")
    @ResponseBody
    public ResponseEntity<String> addSchoolStudents(@RequestBody SchoolStudentDTO schoolStudentDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(schoolStudentDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(4L);
        User user = new User(schoolStudentDTO.getLogin(), hash, salt, userType);
        userService.saveUser(user);

        Class cl = classService.findClassById(schoolStudentDTO.getClassRoomId());
        SchoolStudent schoolStudent = new SchoolStudent(cl, schoolStudentDTO.getFirstName(), schoolStudentDTO.getLastName());
        schoolStudent.setPatronymic(schoolStudentDTO.getPatronymic());
        schoolStudent.setEmail(schoolStudentDTO.getEmail());
        schoolStudent.setPhoneNumber(schoolStudentDTO.getPhoneNumber());
        schoolStudent.setUser(user);
        schoolStudent.setEducationalInstitution(cl.getTeacher().getEducationalInstitution());
        schoolStudentService.saveSchoolStudent(schoolStudent);

        return ResponseEntity.ok("{\"message\": \"Ученик успешно добавлен\"}");
    }

    @GetMapping("/getStudentsOfClass")
    @ResponseBody
    public ResponseEntity<List<SchoolStudent>> getStudentsOfClass(@RequestParam Long ObjectId) {
        List<SchoolStudent> schoolStudents = schoolStudentService.getAllSchoolStudentByClassId(ObjectId);

        if (schoolStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(schoolStudents);
        }

        return ResponseEntity.ok(schoolStudents);
    }

    @DeleteMapping("/deleteSchoolStudent")
    public ResponseEntity<Void> deleteSchoolStudent(@RequestParam Long id) {
        schoolStudentService.deleteSchoolStudentById(id);
        return ResponseEntity.ok().build();
    }
}
