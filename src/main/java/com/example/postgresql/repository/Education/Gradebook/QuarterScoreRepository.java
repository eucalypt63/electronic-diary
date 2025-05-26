package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.QuarterScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuarterScoreRepository extends JpaRepository<QuarterScore, Long> {
    QuarterScore findQuarterScoreBySchoolStudentIdAndSchoolSubjectIdAndQuarterInfoId(Long schoolStudentId,
                                                                                     Long schoolSubjectId,
                                                                                     Long quarterInfoId);

    List<QuarterScore> findQuarterScoreBySchoolStudentIdAndSchoolSubjectId(Long schoolStudentId,
                                                                           Long schoolSubjectId);

    List<QuarterScore> findQuarterScoreBySchoolStudentId(Long schoolStudentId);
}