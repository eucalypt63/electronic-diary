package com.example.postgresql.service.Education;

import com.example.postgresql.repository.Education.Gradebook.ScheduleLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleLessonRepository scheduleLessonRepository;

    //Найти ScheduleLesson по группам

}
