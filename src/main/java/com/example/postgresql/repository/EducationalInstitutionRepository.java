package com.example.postgresql.repository;

import com.example.postgresql.model.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {
}