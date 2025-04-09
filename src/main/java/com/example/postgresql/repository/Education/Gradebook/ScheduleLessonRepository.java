package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleLessonRepository extends JpaRepository<ScheduleLesson, Long> {
    List<ScheduleLesson> findScheduleLessonByGroupIdAndQuarterNumber(Long groupId, Long quarterNumber);
    ScheduleLesson findScheduleLessonByQuarterNumberAndDayNumberAndLessonNumberAndTeacherAssignment_Teacher_Id(
            Long quarterNumber,
            Long dayNumber,
            Long lessonNumber,
            Long teacherId
    );
}