package com.example.postgresql.repository;

import com.example.postgresql.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<Class, Long> {
    Class findClassByTeacherId(Long id);
    List<Class> findAllByTeacherEducationalInstitutionId(Long id);
}