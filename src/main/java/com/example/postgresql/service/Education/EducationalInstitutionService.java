package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitutionType;
import com.example.postgresql.repository.Education.EducationInfo.EducationalInstitutionRepository;
import com.example.postgresql.repository.Education.EducationInfo.EducationalInstitutionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationalInstitutionService {
    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    @Autowired
    private EducationalInstitutionTypeRepository educationalInstitutionTypeRepository;

    public List<EducationalInstitution> findAllEducationalInstitution() {
        return educationalInstitutionRepository.findAll();
    }
    public EducationalInstitution findEducationalInstitutionById(Long id) {
        return educationalInstitutionRepository.findById(id).orElse(null);
    }

    public void saveEducationalInstitutional(EducationalInstitution institution) {
        educationalInstitutionRepository.save(institution);
    }

    public void deleteEducationalInstitutionById(Long id) {
        educationalInstitutionRepository.deleteById(id);
    }


    // Type
    public EducationalInstitutionType findEducationalInstitutionTypeById(Long id) {
        return educationalInstitutionTypeRepository.findById(id).orElse(null);
    }
}
