package com.example.postgresql.repository;

import com.example.postgresql.model.Admin;
import com.example.postgresql.model.ScAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScAdminRepository extends JpaRepository<ScAdmin, Long>{
    ScAdmin findByLogin(String username);
}
