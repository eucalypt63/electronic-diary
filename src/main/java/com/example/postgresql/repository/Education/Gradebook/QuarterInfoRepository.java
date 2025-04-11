package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.QuarterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuarterInfoRepository extends JpaRepository<QuarterInfo, Long> {
    QuarterInfo findQuarterInfoByQuarterNumber(Long quarterNumber);
}