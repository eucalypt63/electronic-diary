package com.example.postgresql.repository.Education.EducationInfo;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {
}