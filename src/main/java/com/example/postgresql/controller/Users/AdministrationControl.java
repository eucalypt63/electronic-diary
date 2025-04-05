package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.RequestDTO.AdministratorRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.AdministratorResponseDTO;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Users.AdministratorService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
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
public class AdministrationControl {

    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private UserService userService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;

    @Autowired
    private DTOService dtoService;


    //Получить администрацию по id школы
    @GetMapping("/getAdministrators")
    @ResponseBody
    public ResponseEntity<List<AdministratorResponseDTO>> getAdministrators(@RequestParam Long schoolId) {
        List<Administrator> administrators = administratorService.findAdministratorByEducationalInstitutionId(schoolId);

        List<AdministratorResponseDTO> administratorResponse = new ArrayList<>();
        for (Administrator admin : administrators) {
            AdministratorResponseDTO dto = dtoService.AdministratorToDto(admin);
            administratorResponse.add(dto);
        }


        if (administratorResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(administratorResponse);
    }

    //Получить администратора по его id
    @GetMapping("/findAdministratorById")
    @ResponseBody
    public ResponseEntity<AdministratorResponseDTO> findAdministratorById(@RequestParam Long id) {
        Administrator administrator = administratorService.findAdministratorById(id);
        AdministratorResponseDTO administratorResponse = dtoService.AdministratorToDto(administrator);

        return ResponseEntity.ok(administratorResponse);
    }

    //Получить школу по id админа
    @GetMapping("/findSchoolByAdministratorId")
    @ResponseBody
    public ResponseEntity<EducationalInstitution> findSchoolByAdministratorId(@RequestParam Long id) {
        Administrator administrator = administratorService.findAdministratorById(id);
        EducationalInstitution educationalInstitution = administrator.getEducationalInstitution();

        if (educationalInstitution == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(educationalInstitution);
    }

    //Добавить админа
    @PostMapping("/addAdministrator")
    @ResponseBody
    public ResponseEntity<String> addAdministrator(@RequestBody AdministratorRequestDTO administratorRequestDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(administratorRequestDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(2L);
        EducationalInstitution educationalInstitution = educationalInstitutionService.
                findEducationalInstitutionById(administratorRequestDTO.getUniversityId());
        User user = new User(administratorRequestDTO.getLogin(), hash, salt);
        user.setUserType(userType);

        userService.saveUser(user);

        Administrator administrator = new Administrator(administratorRequestDTO.getFirstName(), administratorRequestDTO.getLastName());
        administrator.setPatronymic(administratorRequestDTO.getPatronymic());
        administrator.setEmail(administratorRequestDTO.getEmail());
        administrator.setPhoneNumber(administratorRequestDTO.getPhoneNumber());
        administrator.setUser(user);
        administrator.setEducationalInstitution(educationalInstitution);
        administratorService.saveAdministrator(administrator);

        return ResponseEntity.ok("{\"message\": \"Администратор успешно добавлен\"}");
    }

    //Удалить админа
    @DeleteMapping("/deleteAdministrator")
    public ResponseEntity<Void> deleteAdministrator(@RequestParam("id") Long id) {
        administratorService.deleteAdministratorById(id);
        return ResponseEntity.ok().build();
    }

    //Добавление фотографии Администратора
    @PostMapping("/addImageAdministrator")
    @ResponseBody
    public ResponseEntity<String> addImageAdministrator(@RequestParam("image") MultipartFile file, @RequestParam Long id) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", pngOutputStream);
            byte[] pngBytes = pngOutputStream.toByteArray();

            String uploadUrl = "http://77.222.37.9/files/" + "Administrator" + id + ".png";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(pngBytes, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

            Administrator administrator = administratorService.findAdministratorById(id);
            administrator.setPathImage(uploadUrl);
            administratorService.saveAdministrator(administrator);

            return ResponseEntity.ok("{\"message\": \"Image uploaded successfully \"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"Error uploading image \"}");
        }
    }

    @PostMapping("/changeAdministrator")
    @ResponseBody
    public ResponseEntity<String> changeAdministrator(@RequestBody AdministratorRequestDTO administratorRequestDTO) {
        Administrator administrator = administratorService.findAdministratorById(administratorRequestDTO.getId());
        administrator.setFirstName(administratorRequestDTO.getFirstName());
        administrator.setLastName(administratorRequestDTO.getLastName());
        administrator.setPatronymic(administratorRequestDTO.getPatronymic());
        administrator.setEmail(administratorRequestDTO.getEmail());
        administrator.setPhoneNumber(administratorRequestDTO.getPhoneNumber());

        return ResponseEntity.ok().build();
    }
}
