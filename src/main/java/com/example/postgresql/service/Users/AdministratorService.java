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

    public List<Administrator> getAllAdministrator() {
        return administratorRepository.findAll();
    }

    public void saveAdministrator(Administrator administrator) {
        administratorRepository.save(administrator);
    }

    public void deleteAdministratorById(Long id) {
        administratorRepository.deleteById(id);
    }

}
