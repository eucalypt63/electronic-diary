package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.QuarterScore;
import com.example.postgresql.model.Education.Gradebook.YearScore;
import com.example.postgresql.repository.Education.Gradebook.QuarterScoreRepository;
import com.example.postgresql.repository.Education.Gradebook.YearScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearScoreService {

    @Autowired
    private YearScoreRepository yearScoreRepository;

    public YearScore findYearScoreBySchoolStudentIdAndSchoolSubjectId(Long schoolStudentId,
                                                                      Long schoolSubjectId){
        return yearScoreRepository.findYearScoreBySchoolStudentIdAndSchoolSubjectId(schoolStudentId, schoolSubjectId);
    }

    public List<YearScore> findYearScoreBySchoolStudentId(Long schoolStudentId){
        return yearScoreRepository.findYearScoreBySchoolStudentId(schoolStudentId);
    }

    public YearScore  findYearScoreById(Long id){return yearScoreRepository.findById(id).orElse(null);}
    public void saveYearScore(YearScore yearScore){yearScoreRepository.save(yearScore);}
}
