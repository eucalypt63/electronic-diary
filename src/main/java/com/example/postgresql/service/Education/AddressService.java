package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.EducationInfo.Region;
import com.example.postgresql.model.Education.EducationInfo.Settlement;
import com.example.postgresql.repository.Education.EducationInfo.RegionRepository;
import com.example.postgresql.repository.Education.EducationInfo.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private SettlementRepository settlementRepository;
    @Autowired
    private RegionRepository regionRepository;

    public Settlement findSettlementById(Long id) {
        return settlementRepository.findById(id).orElse(null);
    }
    public List<Settlement> getAllSettlement() {
        return settlementRepository.findAll();
    }
    public List<Settlement> findSettlementByRegionId(Long id) {
        return settlementRepository.findSettlementByRegionId(id);
    }

    public List<Region> getAllRegion() {
        return regionRepository.findAll();
    }

}
