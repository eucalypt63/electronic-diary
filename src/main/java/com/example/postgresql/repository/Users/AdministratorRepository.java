package com.example.postgresql.repository.Users;

import com.example.postgresql.model.Users.Administrations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrations, Long> {
    List<Administrations> findAdministratorByEducationalInstitutionId(Long educationalInstitutionId);
    Administrations findAdministratorByUserId(Long userId);
}