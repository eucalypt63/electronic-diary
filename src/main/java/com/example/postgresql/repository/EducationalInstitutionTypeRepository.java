package com.example.postgresql.repository;

import com.example.postgresql.model.EducationalInstitutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalInstitutionTypeRepository extends JpaRepository<EducationalInstitutionType, Long> {
}