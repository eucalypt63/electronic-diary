package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.repository.Education.Gradebook.ScheduleLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleLessonRepository scheduleLessonRepository;

    public List<ScheduleLesson> findScheduleLessonByGroupIdAndQuarterNumber(Long groupId, Long quarterNumber){
        return scheduleLessonRepository.findScheduleLessonByGroupIdAndQuarterNumber(groupId, quarterNumber);
    }

}
