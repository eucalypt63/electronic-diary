package com.example.postgresql.controller.Users;


import com.example.postgresql.DTO.RequestDTO.SchoolStudentRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.SchoolStudentResponseDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Education.GroupService;
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

@Controller
public class SchoolStudentControl {
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private GroupService groupService;
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
        User user = new User(schoolStudentRequestDTO.getLogin(), hash, salt);
        user.setUserType(userType);
        userService.saveUser(user);

        Class cl = classService.findClassById(schoolStudentRequestDTO.getClassRoomId());
        SchoolStudent schoolStudent = new SchoolStudent(schoolStudentRequestDTO.getFirstName(), schoolStudentRequestDTO.getLastName());
        schoolStudent.setClassRoom(cl);
        schoolStudent.setPatronymic(schoolStudentRequestDTO.getPatronymic());
        schoolStudent.setEmail(schoolStudentRequestDTO.getEmail());
        schoolStudent.setPhoneNumber(schoolStudentRequestDTO.getPhoneNumber());
        schoolStudent.setUser(user);
        schoolStudent.setEducationalInstitution(cl.getTeacher().getEducationalInstitution());
        schoolStudentService.saveSchoolStudent(schoolStudent);

        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(groupService.findGroupByClassRoomIdAndGroupName(cl.getId(), "Класс"));
        groupMember.setSchoolStudent(schoolStudent);
        groupService.saveGroupMember(groupMember);

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
    public ResponseEntity<Void> deleteSchoolStudent(@RequestParam Long id){
        schoolStudentService.deleteSchoolStudentById(id);
        return ResponseEntity.ok().build();
    }

    //Добавление фотографии ученика
    @PostMapping("/addImageSchoolStudent")
    @ResponseBody
    public ResponseEntity<String> addImageSchoolStudent(@RequestParam("image") MultipartFile file, @RequestParam Long id) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", pngOutputStream);
            byte[] pngBytes = pngOutputStream.toByteArray();

            String uploadUrl = "http://77.222.37.9/files/" + "SchoolStudent" + id + ".png";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(pngBytes, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

            SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);
            schoolStudent.setPathImage(uploadUrl);
            schoolStudentService.saveSchoolStudent(schoolStudent);

            return ResponseEntity.ok("{\"message\": \"Image uploaded successfully \"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"Error uploading image \"}");
        }
    }

    @PostMapping("/changeSchoolStudent")
    @ResponseBody
    public ResponseEntity<String> changeSchoolStudent(@RequestBody SchoolStudentRequestDTO schoolStudentRequestDTO) {
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(schoolStudentRequestDTO.getId());
        schoolStudent.setFirstName(schoolStudentRequestDTO.getFirstName());
        schoolStudent.setLastName(schoolStudentRequestDTO.getLastName());
        schoolStudent.setPatronymic(schoolStudentRequestDTO.getPatronymic());
        schoolStudent.setEmail(schoolStudentRequestDTO.getEmail());
        schoolStudent.setPhoneNumber(schoolStudentRequestDTO.getPhoneNumber());

        return ResponseEntity.ok().build();
    }
}
