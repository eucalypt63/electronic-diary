package com.example.postgresql.repository.Education.EducationInfo;

import com.example.postgresql.model.Education.EducationInfo.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findSettlementByRegionId(Long id);
}