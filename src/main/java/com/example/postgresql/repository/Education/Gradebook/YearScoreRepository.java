package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.YearScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearScoreRepository extends JpaRepository<YearScore, Long> {
    YearScore findYearScoreBySchoolStudentIdAndSchoolSubjectId(Long schoolStudentId,
                                                               Long schoolSubjectId );

    List<YearScore> findYearScoreBySchoolStudentId(Long schoolStudentId);
}