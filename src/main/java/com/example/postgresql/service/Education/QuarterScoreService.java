package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.QuarterScore;
import com.example.postgresql.repository.Education.Gradebook.QuarterScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuarterScoreService {

    @Autowired
    private QuarterScoreRepository quarterScoreRepository;

    public QuarterScore findQuarterScoreBySchoolStudentIdAndSchoolSubjectIdAndQuarterInfoId(Long schoolStudentId,
                                                                                            Long schoolSubjectId,
                                                                                            Long quarterInfoId){
        return quarterScoreRepository.findQuarterScoreBySchoolStudentIdAndSchoolSubjectIdAndQuarterInfoId(schoolStudentId, schoolSubjectId, quarterInfoId);
    }
    public List<QuarterScore> findQuarterScoreBySchoolStudentIdAndSchoolSubjectId(Long schoolStudentId,
                                                                                  Long schoolSubjectId){
        return quarterScoreRepository.findQuarterScoreBySchoolStudentIdAndSchoolSubjectId(schoolStudentId, schoolSubjectId);
    }

    public List<QuarterScore> findQuarterScoreBySchoolStudentId(Long schoolStudentId){
        return quarterScoreRepository.findQuarterScoreBySchoolStudentId(schoolStudentId);
    }

    public QuarterScore  findQuarterScoreById(Long id){return quarterScoreRepository.findById(id).orElse(null);}
    public void saveQuarterScore(QuarterScore quarterScore){quarterScoreRepository.save(quarterScore);}
}
