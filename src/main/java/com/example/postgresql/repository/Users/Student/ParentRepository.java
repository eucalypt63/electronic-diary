package com.example.postgresql.repository.Users.Student;

import com.example.postgresql.model.Users.Student.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
}