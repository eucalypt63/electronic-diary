package com.example.postgresql.repository.Users.Student;

import com.example.postgresql.model.Users.Student.SchoolStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolStudentRepository extends JpaRepository<SchoolStudent, Long> {
    List<SchoolStudent> findByClassRoomId(Long classRoomId);
    List<SchoolStudent> findSchoolStudentByEducationalInstitutionId(Long educationalInstitutionId);
    SchoolStudent findSchoolStudentByUserId(Long id);
}