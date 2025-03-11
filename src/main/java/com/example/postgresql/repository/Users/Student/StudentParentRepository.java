package com.example.postgresql.repository.Users.Student;

import com.example.postgresql.model.Users.Student.StudentParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentParentRepository extends JpaRepository<StudentParent, Long> {
    List<StudentParent> findStudentParentBySchoolStudentId(Long schoolStudentId);
    List<StudentParent> findStudentParentByParentId(Long schoolStudentId);

}