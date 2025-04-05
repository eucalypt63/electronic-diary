package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.RequestDTO.ParentRequestDTO;
import com.example.postgresql.DTO.RequestDTO.StudentParentRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.ParentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.SchoolStudentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.StudentParentResponseDTO;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.ParentType;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.repository.Users.Student.StudentParentRepository;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Users.ParentService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
    private StudentParentRepository studentParentRepository;

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

    //Получить StudentParent по id родителя
    @GetMapping("/getStudentParentsByParentTd")
    @ResponseBody
    public ResponseEntity<List<StudentParentResponseDTO>> getStudentParentsByParentTd(@RequestParam Long id) {
        List<StudentParent> studentParents = parentService.findStudentParentByParentId(id);

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
        List<Parent> parents = parentService.findParentsByEducationId(id);
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
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);
        List<Parent> allParents = parentService.findParentsByEducationId(schoolStudent.getEducationalInstitution().getId());

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
        User user = new User(parentRequestDTO.getLogin(), hash, salt);
        user.setUserType(userType);

        userService.saveUser(user);

        Parent parent = new Parent(parentRequestDTO.getFirstName(), parentRequestDTO.getLastName());
        parent.setPatronymic(parentRequestDTO.getPatronymic());
        parent.setEmail(parentRequestDTO.getEmail());
        parent.setPhoneNumber(parentRequestDTO.getPhoneNumber());
        parent.setUser(user);
        parent.setEducationalInstitution(educationalInstitution);
        parentService.saveParent(parent);

        ParentType parentType = parentService.findParentTypeById(parentRequestDTO.getParentType());
        StudentParent studentParent = new StudentParent();
        studentParent.setSchoolStudent(schoolStudent);
        studentParent.setParent(parent);
        studentParent.setParentType(parentType);
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

        StudentParent studentParent = new StudentParent();
        studentParent.setSchoolStudent(schoolStudent);
        studentParent.setParent(parent);
        studentParent.setParentType(parentType);
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
        List<StudentParent> studentParents = parentService.findStudentParentByParentId(ObjectId);

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

    //Добавление фотографии Родителя
    @PostMapping("/addImageParent")
    @ResponseBody
    public ResponseEntity<String> addImageParent(@RequestParam("image") MultipartFile file, @RequestParam Long id) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", pngOutputStream);
            byte[] pngBytes = pngOutputStream.toByteArray();

            String uploadUrl = "http://77.222.37.9/files/" + "Parent" + id + ".png";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(pngBytes, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

            Parent parent = parentService.findParentById(id);
            parent.setPathImage(uploadUrl);
            parentService.saveParent(parent);

            return ResponseEntity.ok("{\"message\": \"Image uploaded successfully \"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"Error uploading image \"}");
        }
    }

    @PostMapping("/changeStudentParent")
    @ResponseBody
    public ResponseEntity<Void> changeStudentParent(@RequestBody StudentParentRequestDTO studentParentRequestDTO) {
        StudentParent studentParent = parentService.findStudentParentById(studentParentRequestDTO.getId());
        studentParent.setParentType(parentService.findParentTypeById(studentParentRequestDTO.getParentTypeId()));
        parentService.saveStudentParent(studentParent);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeParent")
    @ResponseBody
    public ResponseEntity<String> changeParent(@RequestBody ParentRequestDTO parentRequestDTO){
        Parent parent = parentService.findParentById(parentRequestDTO.getId());
        parent.setFirstName(parentRequestDTO.getFirstName());
        parent.setLastName(parentRequestDTO.getLastName());
        parent.setPatronymic(parentRequestDTO.getPatronymic());
        parent.setEmail(parentRequestDTO.getEmail());
        parent.setPhoneNumber(parentRequestDTO.getPhoneNumber());

        return ResponseEntity.ok().build();
    }
}
