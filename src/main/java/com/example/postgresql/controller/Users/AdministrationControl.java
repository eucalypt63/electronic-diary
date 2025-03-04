package com.example.postgresql.controller.Users;

import com.example.postgresql.DTO.AdministratorDTO;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.service.Users.AdministratorService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdministrationControl {

    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private UserService userService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;


    @GetMapping("/getAdministrators")
    @ResponseBody
    public ResponseEntity<List<Administrator>> getAdministrators(@RequestParam Long schoolId) {
        List<Administrator> administrators = administratorService.getAllAdministrator()
                .stream()
                .filter(admin -> admin.getUser().getEducationalInstitution().getId().equals(schoolId))
                .collect(Collectors.toList());

        if (administrators.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(administrators);
        }

        return ResponseEntity.ok(administrators);
    }

    @GetMapping("/findAdministratorById")
    @ResponseBody
    public ResponseEntity<Administrator> findAdministratorById(@RequestParam Long id) {
        Administrator administrator = administratorService.findAdministratorById(id);

        if (administrator == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(administrator);
    }

    @GetMapping("/findSchoolByAdministratorId")
    @ResponseBody
    public ResponseEntity<EducationalInstitution> findSchoolByAdministratorId(@RequestParam Long id) {
        Administrator administrator = administratorService.findAdministratorById(id);
        EducationalInstitution educationalInstitution = administrator.getUser().getEducationalInstitution();

        if (educationalInstitution == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(educationalInstitution);
    }

    @PostMapping("/addAdministrator")
    @ResponseBody
    public ResponseEntity<String> addAdministrator(@RequestBody AdministratorDTO administratorDTO) {
        byte[] salt = userService.generateSalt();
        byte[] hash = userService.hashPassword(administratorDTO.getPassword(), salt);
        UserType userType = userService.findUserTypeById(22L);
        EducationalInstitution educationalInstitution = educationalInstitutionService.
                getEducationalInstitutionById(administratorDTO.getUniversityId());
        User user = new User(administratorDTO.getLogin(), hash, salt, userType);
        user.setEducationalInstitution(educationalInstitution);
        userService.saveUser(user);

        Administrator administrator = new Administrator(administratorDTO.getFirstName(), administratorDTO.getLastName());
        administrator.setPatronymic(administratorDTO.getPatronymic());
        administrator.setEmail(administratorDTO.getEmail());
        administrator.setPhoneNumber(administratorDTO.getPhoneNumber());
        administrator.setUser(user);
        administratorService.saveAdministrator(administrator);

        return ResponseEntity.ok("{\"message\": \"Администратор успешно добавлен\"}");
    }

    @DeleteMapping("/deleteAdministrator")
    public ResponseEntity<Void> deleteAdministrator(@RequestParam("id") Long id) {
        administratorService.deleteAdministratorById(id);
        return ResponseEntity.ok().build();
    }
}
