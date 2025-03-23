package com.example.postgresql.repository.Education.EducationInfo;

import com.example.postgresql.model.Education.EducationInfo.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}