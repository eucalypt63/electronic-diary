package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.QuarterInfo;
import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.SchoolSubject;
import com.example.postgresql.repository.Education.Gradebook.QuarterInfoRepository;
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
    @Autowired
    private QuarterInfoRepository quarterInfoRepository;

    public List<ScheduleLesson> findScheduleLessonByGroupIdAndQuarterNumber(Long groupId, Long quarterNumber){
        return scheduleLessonRepository.findByGroupIdAndQuarterInfo_QuarterNumber(groupId, quarterNumber);
    }

    public List<ScheduleLesson> findScheduleLessonByGroupId(Long groupId){
        return scheduleLessonRepository.findScheduleLessonByGroupId(groupId);
    }

    public List<ScheduleLesson> findScheduleLessonByTeacherAssignmentIdAndQuarterNumber(Long teacherId, Long quarterNumber){
        return scheduleLessonRepository.findScheduleLessonByTeacherAssignmentIdAndQuarterInfo_QuarterNumber(teacherId, quarterNumber);
    }

    public boolean checkAvailability(Long quarterNumber, Long dayNumber, Long lessonNumber, Long teacherId){
        return scheduleLessonRepository.findByQuarterInfo_QuarterNumberAndDayNumberAndLessonNumberAndTeacherAssignment_Teacher_Id(
                quarterNumber, dayNumber, lessonNumber, teacherId) == null;
    }

    public SchoolSubject findSchoolSubjectById(Long id){
        return schoolSubjectRepository.findById(id).orElse(null);
    }
    public ScheduleLesson findScheduleLessonById(Long id) {return scheduleLessonRepository.findById(id).orElse(null);}

    public List<SchoolSubject> getAllSchoolSubject(){
        return schoolSubjectRepository.findAll();
    }

    public void saveScheduleLesson (ScheduleLesson scheduleLesson){
        scheduleLessonRepository.save(scheduleLesson);
    }
    public void deleteScheduleLesson (Long id){
        scheduleLessonRepository.deleteById(id);
    }

    public QuarterInfo findQuarterInfoByQuarterNumber(Long id){
        return quarterInfoRepository.findQuarterInfoByQuarterNumber(id);
    }

    public List<ScheduleLesson> findScheduleLessonsByClassAndTime(
            Long classId,
            Integer day,
            Integer lessonNumber,
            Integer quarter) {

        return scheduleLessonRepository
                .findByGroup_ClassRoom_IdAndDayNumberAndLessonNumberAndQuarterInfo_QuarterNumber(
                        classId,
                        day.longValue(),
                        lessonNumber.longValue(),
                        quarter.longValue()
                );
    }

    public List<ScheduleLesson> findScheduleLessonsByTeacherAssignmentIdAndTime(
            Long teacherAssignmentId,
            Integer day,
            Integer lessonNumber,
            Integer quarter) {

        return scheduleLessonRepository
                .findByTeacherAssignmentIdAndDayNumberAndLessonNumberAndQuarterInfo_QuarterNumber(
                        teacherAssignmentId,
                        day.longValue(),
                        lessonNumber.longValue(),
                        quarter.longValue()
                );
    }
}
