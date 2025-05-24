package com.example.postgresql.repository.Users;

import com.example.postgresql.model.Users.LocalOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalOperatorRepository extends JpaRepository<LocalOperator, Long> {
    LocalOperator findLocalOperatorByUserId(Long id);
}