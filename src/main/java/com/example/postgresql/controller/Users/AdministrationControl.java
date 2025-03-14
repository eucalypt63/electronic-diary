package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.RequestDTO.AdministratorRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.AdministratorResponseDTO;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Users.AdministratorService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
                getEducationalInstitutionById(administratorRequestDTO.getUniversityId());
        User user = new User(administratorRequestDTO.getLogin(), hash, salt, userType);

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
}
