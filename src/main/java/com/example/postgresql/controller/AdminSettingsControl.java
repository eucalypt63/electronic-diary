package com.example.postgresql.controller;

import com.example.postgresql.DTO.*;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Education.EducationalInstitutionType;
import com.example.postgresql.model.Users.Education.Region;
import com.example.postgresql.model.Users.Education.Settlement;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.AdminSettingsService;
import com.example.postgresql.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminSettingsControl {

    @Autowired
    private AdminSettingsService adminSettingsService;
    @Autowired
    private LoginService loginService;

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


    // Школа
    @PostMapping("/addEducationalInstitution")
    @ResponseBody
    public ResponseEntity<String> addEducationalInstitution(@RequestBody EducationalInstitutionDTO institutionDTO) {
        EducationalInstitutionType type = adminSettingsService.findEducationalInstitutionTypeById(1L);
        Settlement settlement = adminSettingsService.findSettlementById(institutionDTO.getSettlementId());
        EducationalInstitution educationalInstitution = new EducationalInstitution(institutionDTO.getName(),
                                                                                    institutionDTO.getAddress(),
                                                                                    type,
                                                                                    settlement);
        educationalInstitution.setEmail(institutionDTO.getEmail());
        educationalInstitution.setPhoneNumber(institutionDTO.getPhoneNumber());

        adminSettingsService.saveEducationalInstitutional(educationalInstitution);
        return ResponseEntity.ok("{\"message\": \"Школа успешно добавлена\"}");
    }

    @DeleteMapping("/deleteEducationalInstitution")
    public ResponseEntity<Void> deleteEducationalInstitution(@RequestParam("id") Long id) {
        adminSettingsService.deleteEducationalInstitutionById(id);
        return ResponseEntity.ok().build();
    }


    // Ученики
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

    @PostMapping("/addSchoolStudent")
    @ResponseBody
    public ResponseEntity<String> addSchoolStudents(@RequestBody SchoolStudentDTO schoolStudentDTO) {
        byte[] salt = adminSettingsService.generateSalt();
        byte[] hash = loginService.hashPassword(schoolStudentDTO.getPassword(), salt);
        UserType userType = adminSettingsService.findUserTypeById(23L);
        Class cl = adminSettingsService.findClassById(schoolStudentDTO.getClassRoomId());

        User user = new User(schoolStudentDTO.getLogin(), hash, salt, userType);
        user.setEducationalInstitution(cl.getTeacher().getUser().getEducationalInstitution());
        loginService.saveUser(user);

        SchoolStudent schoolStudent = new SchoolStudent(cl, schoolStudentDTO.getFirstName(), schoolStudentDTO.getLastName());
        schoolStudent.setPatronymic(schoolStudentDTO.getPatronymic());
        schoolStudent.setEmail(schoolStudentDTO.getEmail());
        schoolStudent.setPhoneNumber(schoolStudentDTO.getPhoneNumber());
        schoolStudent.setUser(user);
        adminSettingsService.saveSchoolStudent(schoolStudent);

        return ResponseEntity.ok("{\"message\": \"Ученик успешно добавлен\"}");
    }

    //Регионы
    @GetMapping("/getRegions")
    @ResponseBody
    public ResponseEntity<List<Region>> getRegions() {
        List<Region> regions = adminSettingsService.getAllRegion();

        if (regions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(regions);
        }

        return ResponseEntity.ok(regions);
    }

    //Районы
    @GetMapping("/getSettlements")
    @ResponseBody
    public ResponseEntity<List<Settlement>> getSettlements(@RequestParam Long region) {
        List<Settlement> settlements = adminSettingsService.getAllSettlement()
                .stream()
                .filter(settlement -> settlement.getRegion().getId().equals(region))
                .collect(Collectors.toList());

        if (settlements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(settlements);
        }

        return ResponseEntity.ok(settlements);
    }

    // Администрация
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
    public ResponseEntity<String> addAdministrator(@RequestBody AdministratorDTO administratorDTO) {
        byte[] salt = adminSettingsService.generateSalt();
        byte[] hash = loginService.hashPassword(administratorDTO.getPassword(), salt);
        UserType userType = adminSettingsService.findUserTypeById(22L);
        EducationalInstitution educationalInstitution = adminSettingsService.
                                                        getEducationalInstitutionById(administratorDTO.getUniversityId());
        User user = new User(administratorDTO.getLogin(), hash, salt, userType);
        user.setEducationalInstitution(educationalInstitution);
        loginService.saveUser(user);

        Administrator administrator = new Administrator(administratorDTO.getFirstName(), administratorDTO.getLastName());
        administrator.setPatronymic(administratorDTO.getPatronymic());
        administrator.setEmail(administratorDTO.getEmail());
        administrator.setPhoneNumber(administratorDTO.getPhoneNumber());
        administrator.setUser(user);
        adminSettingsService.saveAdministrator(administrator);

        return ResponseEntity.ok("{\"message\": \"Администратор успешно добавлен\"}");
    }

    @DeleteMapping("/deleteAdministrator")
    public ResponseEntity<Void> deleteAdministrator(@RequestParam("id") Long id) {
        adminSettingsService.deleteAdministratorById(id);
        return ResponseEntity.ok().build();
    }

    // Учителя
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
    public ResponseEntity<String> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        byte[] salt = adminSettingsService.generateSalt();
        byte[] hash = loginService.hashPassword(teacherDTO.getPassword(), salt);
        UserType userType = adminSettingsService.findUserTypeById(21L);
        EducationalInstitution educationalInstitution = adminSettingsService.
                getEducationalInstitutionById(teacherDTO.getUniversityId());
        User user = new User(teacherDTO.getLogin(), hash, salt, userType);
        user.setEducationalInstitution(educationalInstitution);
        loginService.saveUser(user);

        Teacher teacher = new Teacher(teacherDTO.getFirstName(), teacherDTO.getLastName());
        teacher.setPatronymic(teacherDTO.getPatronymic());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setUser(user);
        adminSettingsService.saveTeacher(teacher);

        return ResponseEntity.ok("{\"message\": \"Учитель успешно добавлен\"}");
    }

    @DeleteMapping("/deleteTeacher")
    public ResponseEntity<Void> deleteTeacher(@RequestParam("id") Long id) {
        adminSettingsService.deleteTeacherById(id);
        return ResponseEntity.ok().build();
    }


    // Классы
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

    @PostMapping("/addClass")
    @ResponseBody
    public ResponseEntity<String> addClass(@RequestBody ClassDTO classDTO) {
        System.out.println("Данные учителя: " + classDTO);
        Teacher teacher = adminSettingsService.findTeacherById(classDTO.getTeacherId());
        Class cl = new Class(classDTO.getName(), teacher);

        adminSettingsService.saveClass(cl);
        return ResponseEntity.ok("{\"message\": \"Класс успешно добавлен\"}");
    }

    @DeleteMapping("/deleteClass")
    public ResponseEntity<Void> deleteClass(@RequestParam("id") Long id) {
        adminSettingsService.deleteClassById(id);
        return ResponseEntity.ok().build();
    }

    // Родители
    @GetMapping("/getStudentParents")
    @ResponseBody
    public ResponseEntity<List<Parent>> getParents(@RequestParam(required = false) Long SchoolStudentId) {
        SchoolStudent schoolStudent = adminSettingsService.findSchoolStudentById(SchoolStudentId);
        List<Parent> parents = new ArrayList<>();
        //!!!!!!!!!!!!

        if (parents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(parents);
    }

    @PostMapping("/addParents")
    @ResponseBody
    public ResponseEntity<String> addParents(@RequestBody ParentDTO parentDTO) {
        byte[] salt = adminSettingsService.generateSalt();
        byte[] hash = loginService.hashPassword(parentDTO.getPassword(), salt);
        UserType userType = adminSettingsService.findUserTypeById(24L);
        SchoolStudent schoolStudent = adminSettingsService.findSchoolStudentById(parentDTO.getSchoolStudentId());
        EducationalInstitution educationalInstitution = schoolStudent.getClassRoom().getTeacher().getUser().getEducationalInstitution();
        User user = new User(parentDTO.getLogin(), hash, salt, userType);
        user.setEducationalInstitution(educationalInstitution);
        loginService.saveUser(user);

        Parent studentParents = new Parent(parentDTO.getFirstName(), parentDTO.getLastName());
        studentParents.setPatronymic(parentDTO.getPatronymic());
        studentParents.setEmail(parentDTO.getEmail());
        studentParents.setPhoneNumber(parentDTO.getPhoneNumber());
        studentParents.setUser(user);
        adminSettingsService.saveParent(studentParents);

        return ResponseEntity.ok("{\"message\": \"Родитель успешно добавлен\"}");
    }

    @DeleteMapping("/deleteParent")
    public ResponseEntity<Void> deleteParent(@RequestParam("id") Long id) {
        adminSettingsService.deleteParentById(id);
        return ResponseEntity.ok().build();
    }
}
