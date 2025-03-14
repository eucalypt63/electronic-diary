package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.RequestDTO.ParentRequestDTO;
import com.example.postgresql.DTO.RequestDTO.StudentParentRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.ParentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.SchoolStudentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.StudentParentResponseDTO;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.ParentType;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Users.ParentService;
import com.example.postgresql.service.Users.SchoolStudentService;
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
public class ParentControl {
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private DTOService dtoService;

    @Autowired
    private ParentService parentService;

    //Получить StudentParent по id ученика
    @GetMapping("/getStudentParents")
    @ResponseBody
    public ResponseEntity<List<StudentParentResponseDTO>> getStudentParents(@RequestParam Long id) {
        List<StudentParent> studentParents = parentService.findStudentParentBySchoolStudentId(id);

        List<StudentParentResponseDTO> studentParentResponseDTOS = new ArrayList<>();
        for (StudentParent studentParent : studentParents) {
            StudentParentResponseDTO studentParentResponseDTO = dtoService.StudentParentToDto(studentParent);
            studentParentResponseDTOS.add(studentParentResponseDTO);
        }

        if (studentParentResponseDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(studentParentResponseDTOS);
    }

    //Получить родителя по id
    @GetMapping("/findParentById")
    @ResponseBody
    public ResponseEntity<ParentResponseDTO> findParentById(@RequestParam Long id) {
        Parent parent = parentService.findParentById(id);
        ParentResponseDTO parentResponseDTO = dtoService.ParentToDto(parent);

        return ResponseEntity.ok(parentResponseDTO);
    }

    //Получить родителей по id школы
    @GetMapping("/getParentsByEducationId")
    @ResponseBody
    public ResponseEntity<List<ParentResponseDTO>> getParentsByEducationId(@RequestParam Long id) {
        List<Parent> parents = parentService.getParentsByEducationId(id);
        List<ParentResponseDTO> parentResponseDTOS = new ArrayList<>();
        for (Parent parent : parents){
            ParentResponseDTO parentResponseDTO = dtoService.ParentToDto(parent);
            parentResponseDTOS.add(parentResponseDTO);
        }

        if (parentResponseDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(parentResponseDTOS);
    }

    //Получить родителей исключив родителей по id ребёнка
    @GetMapping("/getNewParents")
    @ResponseBody
    public ResponseEntity<List<ParentResponseDTO>> getNewParents(@RequestParam Long id) {
        List<Parent> allParents = parentService.getAllParents();

        List<StudentParent> studentParents = parentService.findStudentParentBySchoolStudentId(id);

        Set<Long> excludedParentIds = studentParents.stream()
                .map(sp -> sp.getParent().getId())
                .collect(Collectors.toSet());

        List<Parent> filteredParents = allParents.stream()
                .filter(parent -> !excludedParentIds.contains(parent.getId()))
                .toList();

        List<ParentResponseDTO> parentResponseDTOS = new ArrayList<>();
        for (Parent parent : filteredParents){
            ParentResponseDTO parentResponseDTO = dtoService.ParentToDto(parent);
            parentResponseDTOS.add(parentResponseDTO);
        }

        if (parentResponseDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(parentResponseDTOS);
    }

    //Добавить нового родителя
    @PostMapping("/addNewParent")
    @ResponseBody
    public ResponseEntity<String> addNewParent(@RequestBody ParentRequestDTO parentRequestDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(parentRequestDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(5L);
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(parentRequestDTO.getSchoolStudentId());
        EducationalInstitution educationalInstitution = schoolStudent.getEducationalInstitution();
        User user = new User(parentRequestDTO.getLogin(), hash, salt, userType);

        userService.saveUser(user);

        Parent parent = new Parent(parentRequestDTO.getFirstName(), parentRequestDTO.getLastName());
        parent.setPatronymic(parentRequestDTO.getPatronymic());
        parent.setEmail(parentRequestDTO.getEmail());
        parent.setPhoneNumber(parentRequestDTO.getPhoneNumber());
        parent.setUser(user);
        parent.setEducationalInstitution(educationalInstitution);
        parentService.saveParent(parent);

        ParentType parentType = parentService.findParentTypeById(parentRequestDTO.getParentType());
        StudentParent studentParent = new StudentParent(schoolStudent, parent, parentType);
        parentService.saveStudentParent(studentParent);

        return ResponseEntity.ok("{\"message\": \"Родитель успешно добавлен\"}");
    }

    //Добавить уже существующего родителя ребёнку
    @PostMapping("/addParent")
    @ResponseBody
    public ResponseEntity<String> addParent(@RequestBody StudentParentRequestDTO studentParentRequestDTO) {
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(studentParentRequestDTO.getSchoolStudentId());
        Parent parent = parentService.findParentById(studentParentRequestDTO.getParentId());
        ParentType parentType = parentService.findParentTypeById(studentParentRequestDTO.getParentTypeId());

        StudentParent studentParent = new StudentParent(schoolStudent, parent, parentType);
        parentService.saveStudentParent(studentParent);

        return ResponseEntity.ok("{\"message\": \"Родитель успешно добавлен\"}");
    }

    //Удалить родителя
    @DeleteMapping("/deleteParent")
    public ResponseEntity<Void> deleteParent(@RequestParam("id") Long id) {
        parentService.deleteParentById(id);
        return ResponseEntity.ok().build();
    }

    //Удалить связь StudentParent
    @DeleteMapping("/deleteStudentParent")
    public ResponseEntity<Void> deleteStudentParent(@RequestParam("id") Long id) {
        parentService.deleteStudentParentById(id);
        return ResponseEntity.ok().build();
    }

    //Получить тип родитлея
    @GetMapping("/getParentType")
    @ResponseBody
    public ResponseEntity<List<ParentType>> getParentType() {
        List<ParentType> parentTypes = parentService.getAllParentTypes();

        if (parentTypes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(parentTypes);
    }

    //Получить учеников по id родителя
    @GetMapping("/getStudentsOfParent")
    @ResponseBody
    public ResponseEntity<List<SchoolStudentResponseDTO>> getStudentsOfParent(@RequestParam Long ObjectId) {
        List<StudentParent> studentParents = parentService.getAllStudentParentByParentId(ObjectId);

        List<SchoolStudent> schoolStudentList = studentParents.stream()
                .map(StudentParent::getSchoolStudent)
                .toList();

        List<SchoolStudentResponseDTO> schoolStudentResponseDTOS = new ArrayList<>();
        for (SchoolStudent schoolStudent : schoolStudentList){
            SchoolStudentResponseDTO schoolStudentResponseDTO = dtoService.SchoolStudentToDto(schoolStudent);
            schoolStudentResponseDTOS.add(schoolStudentResponseDTO);
        }

        if (schoolStudentResponseDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(schoolStudentResponseDTOS);
    }
}
