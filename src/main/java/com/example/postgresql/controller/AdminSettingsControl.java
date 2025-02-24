package com.example.postgresql.controller;

import com.example.postgresql.DTO.AdministratorDTO;
import com.example.postgresql.DTO.TeacherDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Education.EducationalInstitutionType;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.AdminSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminSettingsControl {

    @Autowired
    private AdminSettingsService adminSettingsService;

    @GetMapping("/getSchools")
    @ResponseBody
    public ResponseEntity<List<EducationalInstitution>> getSchools() {
        List<EducationalInstitution> institutions = adminSettingsService.getAllEducationalInstitution();

        if (institutions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(institutions);
        }

        return ResponseEntity.ok(institutions);
    }

    @GetMapping("/getSchoolsById")
    @ResponseBody
    public ResponseEntity<EducationalInstitution> getSchoolsById(@RequestParam("id") Long id) {
        EducationalInstitution institution = adminSettingsService.getEducationalInstitutionById(id);

        if (institution == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(institution);
        }

        return ResponseEntity.ok(institution);
    }



    @PostMapping("/addEducationalInstitution")
    @ResponseBody
    public ResponseEntity<String> addEducationalInstitution(@RequestBody EducationalInstitution institution) {
        EducationalInstitutionType type = adminSettingsService.findEducationalInstitutionTypeById(1L);
        institution.setEducationalInstitutionType(type);

        adminSettingsService.saveEducationalInstitutionsave(institution);
        return ResponseEntity.ok("{\"message\": \"Школа успешно добавлена\"}");
    }

    @DeleteMapping("/deleteEducationalInstitution")
    public ResponseEntity<Void> deleteEducationalInstitution(@RequestParam("id") Long id) {
        adminSettingsService.delateEducationalInstitutionById(id);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/getSchoolStudents")
    @ResponseBody
    public ResponseEntity<List<SchoolStudent>> getSchoolStudents(@RequestParam Long schoolId) {
        List<SchoolStudent> schoolStudents = adminSettingsService.getAllSchoolStudent()
                .stream()
                .filter(student -> student.getUser().getEducationalInstitution().getId().equals(schoolId))
                .collect(Collectors.toList());

        if (schoolStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(schoolStudents);
        }

        return ResponseEntity.ok(schoolStudents);
    }

    @GetMapping("/getAdministrators")
    @ResponseBody
    public ResponseEntity<List<Administrator>> getAdministrators(@RequestParam Long schoolId) {
        List<Administrator> administrators = adminSettingsService.getAllAdministrator()
                .stream()
                .filter(admin -> admin.getUser().getEducationalInstitution().getId().equals(schoolId))
                .collect(Collectors.toList());

        if (administrators.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(administrators);
        }

        return ResponseEntity.ok(administrators);
    }

    @PostMapping("/addAdministrator")
    @ResponseBody
    public ResponseEntity<String> addAdministrator(@RequestBody AdministratorDTO administrator) {
        System.out.println("Данные администратора: " + administrator);

        return ResponseEntity.ok("{\"message\": \"Администратор успешно добавлен\"}");
    }

    @GetMapping("/getTeachers")
    @ResponseBody
    public ResponseEntity<List<Teacher>> getTeachers(@RequestParam(required = false) Long schoolId) {
        List<Teacher> teachers = adminSettingsService.getAllTeacher()
                .stream()
                .filter(teacher -> teacher.getUser().getEducationalInstitution().getId().equals(schoolId))
                .collect(Collectors.toList());

        if (teachers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(teachers);
        }

        return ResponseEntity.ok(teachers);
    }

    @PostMapping("/addTeacher")
    @ResponseBody
    public ResponseEntity<String> addTeacher(@RequestBody TeacherDTO teacher) {
        System.out.println("Данные учителя: " + teacher);

        return ResponseEntity.ok("{\"message\": \"Учитель успешно добавлен\"}");
    }

    @GetMapping("/getClasses")
    @ResponseBody
    public ResponseEntity<List<Class>> getClasses(@RequestParam(required = false) Long schoolId) {
        List<Class> classes = adminSettingsService.getAllClasses()
                .stream()
                .filter(cl -> cl.getTeacher().getUser().getEducationalInstitution().getId().equals(schoolId))
                .collect(Collectors.toList());

        if (classes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(classes);
        }

        return ResponseEntity.ok(classes);
    }
}
