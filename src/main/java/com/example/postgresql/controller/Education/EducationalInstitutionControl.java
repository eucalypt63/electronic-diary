package com.example.postgresql.controller.Education;


import com.example.postgresql.DTO.RequestDTO.EducationalInstitutionRequestDTO;
import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Users.Administrations.Administrations;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitutionType;
import com.example.postgresql.model.Education.EducationInfo.Settlement;
import com.example.postgresql.model.Users.LocalOperator;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Education.AddressService;
import com.example.postgresql.service.Users.AdministrationsService;
import com.example.postgresql.service.Users.LocalOperatorService;
import com.example.postgresql.service.Users.TeacherService;
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
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EducationalInstitutionControl {
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private AdministrationsService administrationsService;
    @Autowired
    private LocalOperatorService localOperatorService;

    //Получить все школы
    @GetMapping("/getSchools")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<EducationalInstitution>> getSchools() {
        List<EducationalInstitution> institutions = educationalInstitutionService.findAllEducationalInstitution();

        if (institutions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(institutions);
        }

        return ResponseEntity.ok(institutions);
    }

    //Получить школу текущего оператора
    @GetMapping("/getSchoolByAuthorizationAdminId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<EducationalInstitution>> getSchoolByAuthorizationAdminId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        LocalOperator localOperator = localOperatorService.findLocalOperatorByUserId(userId);
        EducationalInstitution institution = educationalInstitutionService.findEducationalInstitutionById(localOperator
                .getEducationalInstitution()
                .getId());
        List<EducationalInstitution> institutions = new ArrayList<>();
        institutions.add(institution);
        return ResponseEntity.ok(institutions);
    }

    //Найти школу по id
    @GetMapping("/findSchoolById")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<EducationalInstitution> findSchoolById(Long id){
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(id);
        return  ResponseEntity.ok(educationalInstitution);
    }

    //Добавить школу
    @PostMapping("/addEducationalInstitution")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> addEducationalInstitution(@RequestBody EducationalInstitutionRequestDTO institutionDTO) {
        Settlement settlement = addressService.findSettlementById(institutionDTO.getSettlementId());
        EducationalInstitution educationalInstitution = new EducationalInstitution(institutionDTO.getName(),
                institutionDTO.getAddress(),
                settlement);
        educationalInstitution.setEmail(institutionDTO.getEmail());
        educationalInstitution.setPhoneNumber(institutionDTO.getPhoneNumber());

        educationalInstitutionService.saveEducationalInstitutional(educationalInstitution);
        return ResponseEntity.ok("{\"message\": \"Школа успешно добавлена\"}");
    }

    //Удалить школу
    @DeleteMapping("/deleteEducationalInstitution")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<Void> deleteEducationalInstitution(@RequestParam Long id) {
        educationalInstitutionService.deleteEducationalInstitutionById(id);
        return ResponseEntity.ok().build();
    }

    //Добавление фотографии Школы
    @PostMapping("/addImageEducational")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> addImageEducational(@RequestParam("image") MultipartFile file, @RequestParam Long id) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", pngOutputStream);
            byte[] pngBytes = pngOutputStream.toByteArray();

            String uploadUrl = "https://77.222.37.9/files/" + "Educational" + id + ".png";

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

            EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(id);
            educationalInstitution.setPathImage(uploadUrl);
            educationalInstitutionService.saveEducationalInstitutional(educationalInstitution);

            return ResponseEntity.ok("{\"message\": \"Image uploaded successfully \"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"Error uploading image \"}");
        }
    }

    @PostMapping("/changeEducationalInstitution")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> changeEducationalInstitution(@RequestBody EducationalInstitutionRequestDTO institutionDTO) {
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(institutionDTO.getId());
        educationalInstitution.setName(institutionDTO.getName());
        educationalInstitution.setAddress(institutionDTO.getAddress());
        educationalInstitution.setEmail(institutionDTO.getEmail());
        educationalInstitution.setPhoneNumber(institutionDTO.getPhoneNumber());
        educationalInstitution.setSettlement(addressService.findSettlementById(institutionDTO.getSettlementId()));
        educationalInstitutionService.saveEducationalInstitutional(educationalInstitution);

        return ResponseEntity.ok().build();
    }
}
