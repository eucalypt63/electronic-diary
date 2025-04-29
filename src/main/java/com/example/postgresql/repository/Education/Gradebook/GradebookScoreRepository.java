package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.GradebookScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradebookScoreRepository extends JpaRepository<GradebookScore, Long> {
    List<GradebookScore> findScoresByGradebookDayId(Long id);
    GradebookScore findScoresByGradebookDayIdAndSchoolStudentId(Long gradebookId, Long schoolStudentId);
}