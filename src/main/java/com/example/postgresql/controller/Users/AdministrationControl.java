package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.RequestDTO.Users.AdministratorRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.AdministratorResponseDTO;
import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Users.Administrations.Administrations;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.Administrations.AdministrationsTypes;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Users.AdministrationsService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Users.UserService;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
    private AdministrationsService administrationsService;
    @Autowired
    private UserService userService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;

    @Autowired
    private DTOService dtoService;


    //Получить администрацию по id школы
    @GetMapping("/getAdministrators")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<AdministratorResponseDTO>> getAdministrators(@RequestParam Long schoolId) {
        List<Administrations> administrations = administrationsService.findAdministrationsByEducationalInstitutionId(schoolId);

        List<AdministratorResponseDTO> administratorResponse = new ArrayList<>();
        for (Administrations admin : administrations) {
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
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<AdministratorResponseDTO> findAdministratorById(@RequestParam Long id) {
        Administrations administrations = administrationsService.findAdministrationById(id);
        AdministratorResponseDTO administratorResponse = dtoService.AdministratorToDto(administrations);

        return ResponseEntity.ok(administratorResponse);
    }

    //Получить школу по id админа
    @GetMapping("/findSchoolByAdministratorId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<EducationalInstitution> findSchoolByAdministratorId(@RequestParam Long id) {
        Administrations administrations = administrationsService.findAdministrationById(id);
        EducationalInstitution educationalInstitution = administrations.getEducationalInstitution();

        if (educationalInstitution == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(educationalInstitution);
    }

    //Получить типы администрации
    @GetMapping("/getAdministrationsType")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<AdministrationsTypes>> getAdministrationsType() {
        return ResponseEntity.ok(administrationsService.getAllAdministrationsTypes());
    }

    //Получить директора школы
    @GetMapping("/findDirectorByEducationId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<AdministratorResponseDTO> findDirectorByEducationId(@RequestParam Long educationId) {
        Administrations administrations = administrationsService.findAdministrationByAdministrationsTypesIdAndEducationalInstitutionId(1L, educationId);
        AdministratorResponseDTO administratorResponse = dtoService.AdministratorToDto(administrations);

        return ResponseEntity.ok(administratorResponse);
    }

    //Добавить админа
    @PostMapping("/addAdministrator")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> addAdministrator(@RequestBody AdministratorRequestDTO administratorRequestDTO) {
        Long administrationsTypesId = administratorRequestDTO.getAdministrationsTypeId();

        if (administrationsTypesId == 1L) {
            Administrations administration = administrationsService.findAdministrationByAdministrationsTypesIdAndEducationalInstitutionId(administrationsTypesId, administratorRequestDTO.getUniversityId());
            if (administration == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"Роль директора уже занята\"}");
            }
        }
        else if (administrationsTypesId == 2L) {
            List<Administrations> administrations = administrationsService.findAdministrationsByAdministrationsTypesId(administrationsTypesId);
            if (!administrations.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"Роль заместителя директора по воспитательной работе уже занята\"}");
            }
        }

        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(administratorRequestDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(21L);
        EducationalInstitution educationalInstitution = educationalInstitutionService.
                findEducationalInstitutionById(administratorRequestDTO.getUniversityId());
        User user = new User(administratorRequestDTO.getLogin(), hash, salt);
        user.setUserType(userType);

        userService.saveUser(user);

        AdministrationsTypes administrationsTypes = administrationsService.findAdministrationsTypeById(administratorRequestDTO.getAdministrationsTypeId());
        Administrations administrations = new Administrations(administratorRequestDTO.getFirstName(), administratorRequestDTO.getLastName());
        administrations.setPatronymic(administratorRequestDTO.getPatronymic());
        administrations.setEmail(administratorRequestDTO.getEmail());
        administrations.setPhoneNumber(administratorRequestDTO.getPhoneNumber());
        administrations.setUser(user);
        administrations.setEducationalInstitution(educationalInstitution);
        administrations.setAdministrationsTypes(administrationsTypes);
        administrationsService.saveAdministration(administrations);

        return ResponseEntity.ok("{\"message\": \"Администратор успешно добавлен\"}");
    }

    //Удалить админа
    @DeleteMapping("/deleteAdministrator")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<Void> deleteAdministrator(@RequestParam("id") Long id) {
        administrationsService.deleteAdministrationById(id);
        return ResponseEntity.ok().build();
    }

    //Добавление фотографии Администратора
    @PostMapping("/addImageAdministrator")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> addImageAdministrator(@RequestParam("image") MultipartFile file, @RequestParam Long id) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", pngOutputStream);
            byte[] pngBytes = pngOutputStream.toByteArray();

            String uploadUrl = "https://77.222.37.9/files/" + "Administrator" + id + ".png";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(pngBytes, headers);

            RestTemplate restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactory(
                    HttpClients.custom()
                        .setSSLContext(
                            SSLContextBuilder.create()
                                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                                .build()
                        )
                        .build()
                )
            );
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

            Administrations administrations = administrationsService.findAdministrationById(id);
            administrations.setPathImage(uploadUrl);
            administrationsService.saveAdministration(administrations);

            return ResponseEntity.ok("{\"message\": \"Image uploaded successfully \"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Error uploading image: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/changeAdministrator")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> changeAdministrator(@RequestBody AdministratorRequestDTO administratorRequestDTO) {
        Administrations administrations = administrationsService.findAdministrationById(administratorRequestDTO.getId());
        administrations.setFirstName(administratorRequestDTO.getFirstName());
        administrations.setLastName(administratorRequestDTO.getLastName());
        administrations.setPatronymic(administratorRequestDTO.getPatronymic());
        administrations.setEmail(administratorRequestDTO.getEmail());
        administrations.setPhoneNumber(administratorRequestDTO.getPhoneNumber());

        //Проверки на доступность
        administrations.setAdministrationsTypes(administrationsService.findAdministrationsTypeById(administratorRequestDTO.getAdministrationsTypeId()));

        if (administratorRequestDTO.getLogin() != null) {
            administrations.getUser().setLogin(administratorRequestDTO.getLogin());
        }
        if (administratorRequestDTO.getPassword() != null){
            byte[] salt = userService.generateSalt();
            byte[] hash = userService.hashPassword(administratorRequestDTO.getPassword(), salt);
            administrations.getUser().setHash(hash);
            administrations.getUser().setSalt(salt);
        }

        administrationsService.saveAdministration(administrations);

        return ResponseEntity.ok().build();
    }
}
