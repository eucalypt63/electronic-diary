package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.GradebookAttendance;
import com.example.postgresql.model.Education.Gradebook.GradebookDay;
import com.example.postgresql.model.Education.Gradebook.GradebookScore;
import com.example.postgresql.repository.Education.Gradebook.GradebookAttendanceRepository;
import com.example.postgresql.repository.Education.Gradebook.GradebookDayRepository;
import com.example.postgresql.repository.Education.Gradebook.GradebookScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradebookService {

    @Autowired
    private GradebookDayRepository gradebookDayRepository;
    @Autowired
    private GradebookScoreRepository gradebookScoreRepository;
    @Autowired
    private GradebookAttendanceRepository gradebookAttendanceRepository;

    public void saveGradebookDay(GradebookDay gradebookDay){gradebookDayRepository.save(gradebookDay);}
    public List<GradebookDay> findGradebookDayByScheduleLessonTeacherAssignmentId(Long id, Long quarterId){
        return gradebookDayRepository.findGradebookDayByScheduleLessonTeacherAssignmentIdAndScheduleLessonQuarterInfoId(id, quarterId);
    }

    public GradebookDay findGradebookDayById(Long id){
        return gradebookDayRepository.findById(id).orElse(null);
    }

    public List<GradebookAttendance> findAttendancesByGradebookDayId(Long id){return gradebookAttendanceRepository.findAttendancesByGradebookDayId(id);}
    public List<GradebookScore> findScoresByGradebookDayId(Long id){return gradebookScoreRepository.findScoresByGradebookDayId(id);}

    public GradebookScore findScoresByGradebookDayIdAndSchoolStudentId(Long gradebookId, Long schoolStudentId){return gradebookScoreRepository.findScoresByGradebookDayIdAndSchoolStudentId(gradebookId, schoolStudentId);}

    public void deleteGradebookScore(GradebookScore gradebookScore){gradebookScoreRepository.delete(gradebookScore);}

    public void saveGradebookScore(GradebookScore gradebookScore){gradebookScoreRepository.save(gradebookScore);}

    public GradebookAttendance findAttendancesByGradebookDayIdAndSchoolStudentId(Long gradebookId, Long schoolStudentId){return gradebookAttendanceRepository.findAttendancesByGradebookDayIdAndSchoolStudentId(gradebookId, schoolStudentId);}

    public void saveGradebookAttendance(GradebookAttendance gradebookAttendance){gradebookAttendanceRepository.save(gradebookAttendance);}
    public void deleteGradebookAttendances(GradebookAttendance gradebookAttendance){gradebookAttendanceRepository.delete(gradebookAttendance);}
    //Нахождение по id дня для посещаемости и оценок

}
