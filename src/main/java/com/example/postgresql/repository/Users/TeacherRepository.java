package com.example.postgresql.repository.Users;

import com.example.postgresql.model.Users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findTeacherByEducationalInstitutionId(Long schoolId);
    Teacher findTeacherByUserId (Long id);
}