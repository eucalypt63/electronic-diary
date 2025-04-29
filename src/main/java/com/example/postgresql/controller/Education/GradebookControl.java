package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookAttendanceResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookDayResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookScoreResponseDTO;
import com.example.postgresql.model.Education.Gradebook.*;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.GradebookService;
import com.example.postgresql.service.Users.SchoolStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GradebookControl {
    @Autowired
    private GradebookService gradebookService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("findGradebookDayByScheduleLessonTeacherAssignmentId")
    @ResponseBody
    public ResponseEntity<GradebookResponseDTO>findGradebookDayByScheduleLessonTeacherAssignmentId(@RequestParam Long id, @RequestParam Long quarterId){
        List<GradebookDay> gradebookDays = gradebookService.findGradebookDayByScheduleLessonTeacherAssignmentId(id, quarterId);
        List<GradebookDayResponseDTO> gradebookDayResponseDTOS = new ArrayList<>();
        List<GradebookAttendanceResponseDTO> attendanceResponseDTOS = new ArrayList<>();
        List<GradebookScoreResponseDTO> scoreResponseDTOS = new ArrayList<>();

        for (GradebookDay gradebookDay : gradebookDays) {
            gradebookDayResponseDTOS.add(dtoService.GradebookDayToDto(gradebookDay));

            List<GradebookAttendance> attendances = gradebookService.findAttendancesByGradebookDayId(gradebookDay.getId());
            attendances.forEach(attendance -> {
                attendanceResponseDTOS.add(dtoService.GradebookAttendanceToDto(attendance));
            });

            List<GradebookScore> scores = gradebookService.findScoresByGradebookDayId(gradebookDay.getId());
            scores.forEach(score -> {
                scoreResponseDTOS.add(dtoService.GradebookScoreToDto(score));
            });
        }

        GradebookResponseDTO responseDTO = new GradebookResponseDTO();
        responseDTO.setGradebookDayResponseDTOS(gradebookDayResponseDTOS);
        responseDTO.setGradebookAttendanceResponseDTOS(attendanceResponseDTOS);
        responseDTO.setGradebookScoreResponseDTOS(scoreResponseDTOS);

        return ResponseEntity.ok(responseDTO);

    }

    @PostMapping("updateGradebookDay")
    @ResponseBody
    public ResponseEntity<String> updateGradebookDay(@RequestParam Long id, @RequestParam String topic, @RequestParam String homework) {
        GradebookDay gradebookDay = gradebookService.findGradebookDayById(id);
        gradebookDay.setTopic(topic);
        gradebookDay.setHomework(homework);
        gradebookService.saveGradebookDay(gradebookDay);

        return ResponseEntity.ok("{\"message\": \"Информация успешно обновлена\"}");
    }

    @PostMapping("updateGradebookScore")
    @ResponseBody
    public ResponseEntity<String> updateGradebookScore(@RequestParam Long gradebookDayId, @RequestParam Long schoolStudentId, @RequestParam Long score) {
        GradebookScore gradebookScore = gradebookService.findScoresByGradebookDayIdAndSchoolStudentId(gradebookDayId, schoolStudentId);
        GradebookAttendance gradebookAttendance = gradebookService.findAttendancesByGradebookDayIdAndSchoolStudentId(gradebookDayId, schoolStudentId);

        if (gradebookScore != null) {
            gradebookScore.setScore(score);
            gradebookService.saveGradebookScore(gradebookScore);
        } else {
            GradebookScore gradebookScore1 = new GradebookScore();
            gradebookScore1.setGradebookDay(gradebookService.findGradebookDayById(gradebookDayId));
            gradebookScore1.setSchoolStudent(schoolStudentService.findSchoolStudentById(schoolStudentId));
            gradebookScore1.setScore(score);
            gradebookService.saveGradebookScore(gradebookScore1);
        }

        if (gradebookAttendance != null){
            gradebookService.deleteGradebookAttendances(gradebookAttendance);
        }

        return ResponseEntity.ok("{\"message\": \"Информация успешно обновлена\"}");
    }

    @PostMapping("updateGradebookAttendance")
    @ResponseBody
    public ResponseEntity<String> updateGradebookAttendance(@RequestParam Long gradebookDayId, @RequestParam Long schoolStudentId) {
        GradebookAttendance gradebookAttendance = gradebookService.findAttendancesByGradebookDayIdAndSchoolStudentId(gradebookDayId, schoolStudentId);
        if (gradebookAttendance != null){
            return ResponseEntity.ok("{\"message\": \"Информация успешно обновлена\"}");
        } else {
            GradebookAttendance gradebookAttendance1 = new GradebookAttendance();
            gradebookAttendance1.setGradebookDay(gradebookService.findGradebookDayById(gradebookDayId));
            gradebookAttendance1.setSchoolStudent(schoolStudentService.findSchoolStudentById(schoolStudentId));
            gradebookService.saveGradebookAttendance(gradebookAttendance1);
        }
        GradebookScore gradebookScore = gradebookService.findScoresByGradebookDayIdAndSchoolStudentId(gradebookDayId, schoolStudentId);
        if (gradebookScore != null){
            gradebookService.deleteGradebookScore(gradebookScore);
        }

        return ResponseEntity.ok("{\"message\": \"Информация успешно обновлена\"}");
    }

}
