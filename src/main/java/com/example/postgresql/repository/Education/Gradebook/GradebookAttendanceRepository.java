package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.GradebookAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradebookAttendanceRepository extends JpaRepository<GradebookAttendance, Long> {
    List<GradebookAttendance> findAttendancesByGradebookDayId(Long id);
    GradebookAttendance findAttendancesByGradebookDayIdAndSchoolStudentId(Long gradebookId, Long schoolStudentId);
}