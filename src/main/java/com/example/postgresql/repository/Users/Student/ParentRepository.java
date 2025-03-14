package com.example.postgresql.repository.Users.Student;

import com.example.postgresql.model.Users.Student.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    List<Parent> findParentByEducationalInstitutionId(Long educationalInstitutionId);
}