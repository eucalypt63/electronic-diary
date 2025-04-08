package com.example.postgresql.service.Users;

import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.Student.SchoolStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }
    public Administrator findAdministratorByUserId(Long id) {
        return administratorRepository.findAdministratorByUserId(id);
    }

    public List<Administrator> findAdministratorByEducationalInstitutionId(Long id) {
        return administratorRepository.findAdministratorByEducationalInstitutionId(id);
    }

    public Administrator findAdministratorById(Long id) {
        return administratorRepository.findById(id).orElse(null);
    }

    public void saveAdministrator(Administrator administrator) {
        administratorRepository.save(administrator);
    }

    public void deleteAdministratorById(Long id) {
        administratorRepository.deleteById(id);
    }

}
