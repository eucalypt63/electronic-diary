package com.example.postgresql.repository.Users.Administrations;

import com.example.postgresql.model.Users.Administrations.AdministrationsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrationsTypesRepository extends JpaRepository<AdministrationsTypes, Long> {
}