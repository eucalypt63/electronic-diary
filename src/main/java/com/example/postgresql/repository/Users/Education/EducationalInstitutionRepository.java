package com.example.postgresql.repository.Users.Education;

import com.example.postgresql.model.Users.Education.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {
}