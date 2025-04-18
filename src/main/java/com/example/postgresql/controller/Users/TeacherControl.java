package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.RequestDTO.TeacherRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.TeacherResponseDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Users.TeacherService;
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
        List<Class> classes = classService.findAllByTeacherEducationalInstitutionId(schoolId);

        Set<Long> assignedTeacherIds = classes.stream()
                .map(classEntity -> classEntity.getTeacher().getId())
                .collect(Collectors.toSet());

        List<Teacher> teachers = teacherService.getTeachersBySchoolId(schoolId, new ArrayList<>(assignedTeacherIds));

        List<TeacherResponseDTO> teacherResponseDTOS = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherResponseDTOS.add(dtoService.TeacherToDto(teacher));
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
                findEducationalInstitutionById(teacherRequestDTO.getUniversityId());
        User user = new User(teacherRequestDTO.getLogin(), hash, salt);
        user.setUserType(userType);
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

    //Добавление фотографии Учителя
    @PostMapping("/addImageTeacher")
    @ResponseBody
    public ResponseEntity<String> addImageTeacher(@RequestParam("image") MultipartFile file, @RequestParam Long id) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", pngOutputStream);
            byte[] pngBytes = pngOutputStream.toByteArray();

            String uploadUrl = "http://77.222.37.9/files/" + "Teacher" + id + ".png";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(pngBytes, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

            Teacher teacher = teacherService.findTeacherById(id);
            teacher.setPathImage(uploadUrl);
            teacherService.saveTeacher(teacher);

            return ResponseEntity.ok("{\"message\": \"Image uploaded successfully \"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"Error uploading image \"}");
        }
    }

    @PostMapping("/changeTeacher")
    @ResponseBody
    public ResponseEntity<String> changeTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = teacherService.findTeacherById(teacherRequestDTO.getId());
        teacher.setFirstName(teacherRequestDTO.getFirstName());
        teacher.setLastName(teacherRequestDTO.getLastName());
        teacher.setPatronymic(teacherRequestDTO.getPatronymic());
        teacher.setEmail(teacherRequestDTO.getEmail());
        teacher.setPhoneNumber(teacherRequestDTO.getPhoneNumber());

        if (teacherRequestDTO.getLogin() != null) {
            teacher.getUser().setLogin(teacherRequestDTO.getLogin());
        }
        if (teacherRequestDTO.getPassword() != null){
            byte[] salt = userService.generateSalt();
            byte[] hash = userService.hashPassword(teacherRequestDTO.getPassword(), salt);
            teacher.getUser().setHash(hash);
            teacher.getUser().setSalt(salt);
        }

        teacherService.saveTeacher(teacher);

        return ResponseEntity.ok().build();
    }
}
