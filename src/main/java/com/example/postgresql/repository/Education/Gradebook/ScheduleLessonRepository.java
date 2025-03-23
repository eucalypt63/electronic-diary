package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleLessonRepository extends JpaRepository<ScheduleLesson, Long> {
}