package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.ParentDTO;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.Users.ParentService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ParentControl {
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolStudentService schoolStudentService;

    @Autowired
    private ParentService parentService;


    @GetMapping("/getStudentParents")
    @ResponseBody
    public ResponseEntity<List<Parent>> getStudentParents(@RequestParam Long ObjectId) {
        List<Parent> parents = parentService.getAllStudentParent().stream()
                .filter(student -> student.getSchoolStudent().getId().equals(ObjectId))
                .map(StudentParent::getParent)
                .collect(Collectors.toList());

        if (parents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(parents);
    }

    @PostMapping("/addParents")
    @ResponseBody
    public ResponseEntity<String> addParents(@RequestBody ParentDTO parentDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(parentDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(24L);
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(parentDTO.getSchoolStudentId());
        EducationalInstitution educationalInstitution = schoolStudent.getClassRoom().getTeacher().getUser().getEducationalInstitution();
        User user = new User(parentDTO.getLogin(), hash, salt, userType);
        user.setEducationalInstitution(educationalInstitution);
        userService.saveUser(user);

        Parent studentParents = new Parent(parentDTO.getFirstName(), parentDTO.getLastName());
        studentParents.setPatronymic(parentDTO.getPatronymic());
        studentParents.setEmail(parentDTO.getEmail());
        studentParents.setPhoneNumber(parentDTO.getPhoneNumber());
        studentParents.setUser(user);
        parentService.saveParent(studentParents);

        return ResponseEntity.ok("{\"message\": \"Родитель успешно добавлен\"}");
    }

    @DeleteMapping("/deleteParent")
    public ResponseEntity<Void> deleteParent(@RequestParam("id") Long id) {
        parentService.deleteParentById(id);
        return ResponseEntity.ok().build();
    }
}
