package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.ParentDTO;
import com.example.postgresql.DTO.StudentParentDTO;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.ParentType;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.Users.ParentService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
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
    public ResponseEntity<List<StudentParent>> getStudentParents(@RequestParam Long id) {
        List<StudentParent> studentParents = parentService.findStudentParentBySchoolStudentId(id);

        if (studentParents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(studentParents);
    }

    @GetMapping("/findParentById")
    @ResponseBody
    public ResponseEntity<Parent> findParentById(@RequestParam Long id) {
        Parent parent = parentService.findParentById(id);

        return ResponseEntity.ok(parent);
    }

    @GetMapping("/getNewParents")
    @ResponseBody
    public ResponseEntity<List<Parent>> getNewParents(@RequestParam Long id) {
        List<Parent> allParents = parentService.getAllParents();

        List<StudentParent> studentParents = parentService.findStudentParentBySchoolStudentId(id);

        Set<Long> excludedParentIds = studentParents.stream()
                .map(sp -> sp.getParent().getId())
                .collect(Collectors.toSet());

        List<Parent> filteredParents = allParents.stream()
                .filter(parent -> !excludedParentIds.contains(parent.getId()))
                .toList();

        if (filteredParents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(filteredParents);
    }


    @PostMapping("/addNewParent")
    @ResponseBody
    public ResponseEntity<String> addNewParent(@RequestBody ParentDTO parentDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(parentDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(5L);
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(parentDTO.getSchoolStudentId());
        EducationalInstitution educationalInstitution = schoolStudent.getEducationalInstitution();
        User user = new User(parentDTO.getLogin(), hash, salt, userType);

        userService.saveUser(user);

        Parent parent = new Parent(parentDTO.getFirstName(), parentDTO.getLastName());
        parent.setPatronymic(parentDTO.getPatronymic());
        parent.setEmail(parentDTO.getEmail());
        parent.setPhoneNumber(parentDTO.getPhoneNumber());
        parent.setUser(user);
        parent.setEducationalInstitution(educationalInstitution);
        parentService.saveParent(parent);

        ParentType parentType = parentService.findParentTypeById(parentDTO.getParentType());
        StudentParent studentParent = new StudentParent(schoolStudent, parent, parentType);
        parentService.saveStudentParent(studentParent);

        return ResponseEntity.ok("{\"message\": \"Родитель успешно добавлен\"}");
    }

    @PostMapping("/addParent")
    @ResponseBody
    public ResponseEntity<String> addParent(@RequestBody StudentParentDTO studentParentDTO) {
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(studentParentDTO.getSchoolStudentId());
        Parent parent = parentService.findParentById(studentParentDTO.getParentId());
        ParentType parentType = parentService.findParentTypeById(studentParentDTO.getParentTypeId());

        StudentParent studentParent = new StudentParent(schoolStudent, parent, parentType);
        parentService.saveStudentParent(studentParent);

        return ResponseEntity.ok("{\"message\": \"Родитель успешно добавлен\"}");
    }

    @DeleteMapping("/deleteParent")
    public ResponseEntity<Void> deleteParent(@RequestParam("id") Long id) {
        parentService.deleteParentById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteStudentParent")
    public ResponseEntity<Void> deleteStudentParent(@RequestParam("id") Long id) {
        parentService.deleteStudentParentById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getParentType")
    @ResponseBody
    public ResponseEntity<List<ParentType>> getParentType() {
        List<ParentType> parentTypes = parentService.getAllParentTypes();

        if (parentTypes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(parentTypes);
    }

    @GetMapping("/getStudentsOfParent")
    @ResponseBody
    public ResponseEntity<List<SchoolStudent>> getStudentsOfParent(@RequestParam Long ObjectId) {
        List<StudentParent> studentParents = parentService.getAllStudentParentByParentId(ObjectId);

        List<SchoolStudent> schoolStudentList = studentParents.stream()
                .map(StudentParent::getSchoolStudent)
                .toList();

        if (schoolStudentList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(schoolStudentList);
    }
}
