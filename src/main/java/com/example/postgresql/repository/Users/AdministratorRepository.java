package com.example.postgresql.repository.Users;

import com.example.postgresql.model.Users.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    List<Administrator> findAdministratorByEducationalInstitutionId(Long educationalInstitutionId);
    Administrator findAdministratorByUserId(Long userId);
}