package com.example.postgresql.controller.Education;


import com.example.postgresql.DTO.EducationalInstitutionDTO;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Education.EducationalInstitutionType;
import com.example.postgresql.model.Users.Education.Settlement;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Education.AddressService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<String> addEducationalInstitution(@RequestBody EducationalInstitutionDTO institutionDTO) {
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
}
