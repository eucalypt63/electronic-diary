package com.example.postgresql.repository.Users.Education;

import com.example.postgresql.model.Users.Education.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findSettlementByRegionId(Long id);
}