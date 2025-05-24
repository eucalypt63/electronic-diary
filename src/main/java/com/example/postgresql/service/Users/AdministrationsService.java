package com.example.postgresql.service.Users;

import com.example.postgresql.model.Users.Administrations.Administrations;
import com.example.postgresql.model.Users.Administrations.AdministrationsTypes;
import com.example.postgresql.repository.Users.Administrations.AdministrationsRepository;
import com.example.postgresql.repository.Users.Administrations.AdministrationsTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrationsService {

    @Autowired
    private AdministrationsRepository administrationsRepository;

    @Autowired
    private AdministrationsTypesRepository administrationsTypesRepository;

    public Administrations findAdministrationByUserId(Long id) {
        return administrationsRepository.findAdministrationsByUserId(id);
    }

    public List<Administrations> findAdministrationsByEducationalInstitutionId(Long id) {
        return administrationsRepository.findAdministrationsByEducationalInstitutionId(id);
    }

    public Administrations findAdministrationById(Long id) {
        return administrationsRepository.findById(id).orElse(null);
    }

    public void saveAdministration(Administrations administrations) {
        administrationsRepository.save(administrations);
    }

    public void deleteAdministrationById(Long id) {
        administrationsRepository.deleteById(id);
    }
    public List<Administrations> findAdministrationsByAdministrationsTypesId(Long id){ return administrationsRepository.findAdministrationsByAdministrationsTypesId(id);}


    public AdministrationsTypes findAdministrationsTypeById(Long id){return administrationsTypesRepository.findById(id).orElse(null);}
    public List<AdministrationsTypes> getAllAdministrationsTypes(){return administrationsTypesRepository.findAll();}
}
