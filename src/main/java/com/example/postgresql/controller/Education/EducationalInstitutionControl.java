package com.example.postgresql.controller.Education;


import com.example.postgresql.DTO.RequestDTO.EducationalInstitutionRequestDTO;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitutionType;
import com.example.postgresql.model.Education.EducationInfo.Settlement;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Education.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    //Получить все школы
    @GetMapping("/getSchools")
    @ResponseBody
    public ResponseEntity<List<EducationalInstitution>> getSchools() {
        List<EducationalInstitution> institutions = educationalInstitutionService.getAllEducationalInstitution();

        if (institutions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(institutions);
        }

        return ResponseEntity.ok(institutions);
    }

    //Получить школу текущего пользователя
    @GetMapping("/getSchoolById")
    @ResponseBody
    public ResponseEntity<List<EducationalInstitution>> getSchoolById(HttpSession session) {
        Administrator administrator = (Administrator) session.getAttribute("user");
        EducationalInstitution institution = educationalInstitutionService.getEducationalInstitutionById(administrator
                .getEducationalInstitution()
                .getId());
        List<EducationalInstitution> institutions = new ArrayList<>();
        institutions.add(institution);
        return ResponseEntity.ok(institutions);
    }

    //Добавить школу
    @PostMapping("/addEducationalInstitution")
    @ResponseBody
    public ResponseEntity<String> addEducationalInstitution(@RequestBody EducationalInstitutionRequestDTO institutionDTO) {
        EducationalInstitutionType type = educationalInstitutionService.findEducationalInstitutionTypeById(1L);
        Settlement settlement = addressService.findSettlementById(institutionDTO.getSettlementId());
        EducationalInstitution educationalInstitution = new EducationalInstitution(institutionDTO.getName(),
                institutionDTO.getAddress(),
                type,
                settlement);
        educationalInstitution.setEmail(institutionDTO.getEmail());
        educationalInstitution.setPhoneNumber(institutionDTO.getPhoneNumber());

        educationalInstitutionService.saveEducationalInstitutional(educationalInstitution);
        return ResponseEntity.ok("{\"message\": \"Школа успешно добавлена\"}");
    }

    //Удалить школу
    @DeleteMapping("/deleteEducationalInstitution")
    public ResponseEntity<Void> deleteEducationalInstitution(@RequestParam Long id) {
        educationalInstitutionService.deleteEducationalInstitutionById(id);
        return ResponseEntity.ok().build();
    }

    //Добавление фотографии Школы
    @PostMapping("/addImageEducational")
    @ResponseBody
    public ResponseEntity<String> addImageEducational(@RequestParam("image") MultipartFile file, @RequestParam Long id) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", pngOutputStream);
            byte[] pngBytes = pngOutputStream.toByteArray();

            String uploadUrl = "http://77.222.37.9/files/" + "Educational" + id + ".png";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(pngBytes, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

            EducationalInstitution educationalInstitution = educationalInstitutionService.getEducationalInstitutionById(id);
            educationalInstitution.setPathImage(uploadUrl);
            educationalInstitutionService.saveEducationalInstitutional(educationalInstitution);

            return ResponseEntity.ok("{\"message\": \"Image uploaded successfully \"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"Error uploading image \"}");
        }
    }
}
