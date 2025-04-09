package com.example.postgresql.repository;

import com.example.postgresql.model.TeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long> {
    TeacherAssignment findTeacherAssignmentByGroupIdAndSchoolSubjectIdAndTeacherId (
            Long groupId,
            Long schoolSubjectId,
            Long teacherId
    );
}