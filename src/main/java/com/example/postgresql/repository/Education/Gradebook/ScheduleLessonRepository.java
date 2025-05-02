package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleLessonRepository extends JpaRepository<ScheduleLesson, Long> {
    List<ScheduleLesson> findByGroupIdAndQuarterInfo_QuarterNumber(Long groupId, Long quarterNumber);
    ScheduleLesson findByQuarterInfo_QuarterNumberAndDayNumberAndLessonNumberAndTeacherAssignment_Teacher_Id(
            Long quarterNumber,
            Long dayNumber,
            Long lessonNumber,
            Long teacherId
    );

    List<ScheduleLesson> findByGroup_ClassRoom_IdAndDayNumberAndLessonNumberAndQuarterInfo_QuarterNumber(
            Long classId,
            Long dayNumber,
            Long lessonNumber,
            Long quarterNumber);

    List<ScheduleLesson> findScheduleLessonByTeacherAssignmentIdAndQuarterInfo_QuarterNumber(Long teacherId, Long quarterNumber);
    List<ScheduleLesson> findByTeacherAssignmentIdAndDayNumberAndLessonNumberAndQuarterInfo_QuarterNumber(
            Long teacherAssignmentId,
            Long dayNumber,
            Long lessonNumber,
            Long quarterNumber);
}