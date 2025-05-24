package com.example.postgresql.repository.Users.Administrations;

import com.example.postgresql.model.Users.Administrations.Administrations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrationsRepository extends JpaRepository<Administrations, Long> {
    List<Administrations> findAdministrationsByEducationalInstitutionId(Long educationalInstitutionId);
    Administrations findAdministrationsByUserId(Long userId);
    List<Administrations> findAdministrationsByAdministrationsTypesId(Long id);

}