package com.example.postgresql.service.Users;

import com.example.postgresql.model.Users.Administrations;
import com.example.postgresql.repository.Users.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrations findAdministratorByUserId(Long id) {
        return administratorRepository.findAdministratorByUserId(id);
    }

    public List<Administrations> findAdministratorByEducationalInstitutionId(Long id) {
        return administratorRepository.findAdministratorByEducationalInstitutionId(id);
    }

    public Administrations findAdministratorById(Long id) {
        return administratorRepository.findById(id).orElse(null);
    }

    public void saveAdministrator(Administrations administrations) {
        administratorRepository.save(administrations);
    }

    public void deleteAdministratorById(Long id) {
        administratorRepository.deleteById(id);
    }

}
