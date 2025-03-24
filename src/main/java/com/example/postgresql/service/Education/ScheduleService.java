package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.Schedule;
import com.example.postgresql.model.Education.Message;
import com.example.postgresql.repository.Education.Gradebook.ScheduleLessonRepository;
import com.example.postgresql.repository.Education.Gradebook.ScheduleRepository;
import com.example.postgresql.repository.Education.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleLessonRepository scheduleLessonRepository;

    public Schedule findScheduleRepositoryById(Long id){
        return scheduleRepository.findById(id).orElse(null);
    }

    //Найти ScheduleLesson по группам

}
