package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.SchoolSubject;
import com.example.postgresql.repository.Education.Gradebook.ScheduleLessonRepository;
import com.example.postgresql.repository.SchoolSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleLessonRepository scheduleLessonRepository;
    @Autowired
    private SchoolSubjectRepository schoolSubjectRepository;

    public List<ScheduleLesson> findScheduleLessonByGroupIdAndQuarterNumber(Long groupId, Long quarterNumber){
        return scheduleLessonRepository.findScheduleLessonByGroupIdAndQuarterNumber(groupId, quarterNumber);
    }

    public boolean checkAvailability(Long quarterNumber, Long dayNumber, Long lessonNumber, Long teacherId){
        if (scheduleLessonRepository.findScheduleLessonByQuarterNumberAndDayNumberAndLessonNumberAndTeacherAssignment_Teacher_Id
                                                                        (quarterNumber, dayNumber, lessonNumber, teacherId) != null){
            return false;
        }
        else {return  true;}
    }

    public SchoolSubject findSchoolSubjectById(Long id){
        return schoolSubjectRepository.findById(id).orElse(null);
    }

    public List<SchoolSubject> getAllSchoolSubject(){
        return schoolSubjectRepository.findAll();
    }

    public void saveScheduleLesson (ScheduleLesson scheduleLesson){
        scheduleLessonRepository.save(scheduleLesson);
    }

}
