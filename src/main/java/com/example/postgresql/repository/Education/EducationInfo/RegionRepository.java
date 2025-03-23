package com.example.postgresql.repository.Education.EducationInfo;

import com.example.postgresql.model.Education.EducationInfo.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}