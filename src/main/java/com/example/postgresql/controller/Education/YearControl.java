package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.ResponseDTO.QuarterScoreResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.YearScoreResponseDTO;
import com.example.postgresql.model.Education.Gradebook.QuarterScore;
import com.example.postgresql.model.Education.Gradebook.YearScore;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.service.Education.GroupService;
import com.example.postgresql.service.Education.QuarterScoreService;
import com.example.postgresql.service.Education.ScheduleService;
import com.example.postgresql.service.Education.YearScoreService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class YearControl {
    @Autowired
    private YearScoreService yearScoreService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SchoolStudentService schoolStudentService;

    @GetMapping("findYearScoreBySchoolStudentId")
    @ResponseBody
    public ResponseEntity<List<YearScoreResponseDTO>> findYearScoreBySchoolStudentId(@RequestParam Long schoolStudentId){
        List<YearScore> yearScores = yearScoreService.findYearScoreBySchoolStudentId(schoolStudentId);
        List<YearScoreResponseDTO> yearScoreResponseDTOS = new ArrayList<>();
        yearScores.forEach(yearScore -> {
            yearScoreResponseDTOS.add(new YearScoreResponseDTO(yearScore.getId(), schoolStudentId, yearScore.getSchoolSubject(), yearScore.getScore()));
        });

        return ResponseEntity.ok(yearScoreResponseDTOS);
    }

    @GetMapping("findYearScoreByTeacherAssignmentId")
    @ResponseBody
    public ResponseEntity<List<YearScoreResponseDTO>> findYearScoreByGroupId(@RequestParam Long teacherAssignmentId){
        TeacherAssignment teacherAssignment = teacherService.findTeacherAssignmentById(teacherAssignmentId);
        List<GroupMember> groupMembers = groupService.findGroupMemberByGroupId(teacherAssignment.getGroup().getId());

        List<YearScoreResponseDTO> yearScoreResponseDTOS = new ArrayList<>();
        groupMembers.forEach(groupMember -> {
            YearScore yearScore = yearScoreService.findYearScoreBySchoolStudentIdAndSchoolSubjectId(groupMember.getSchoolStudent().getId(), teacherAssignment.getSchoolSubject().getId());
            if (yearScore != null){
                yearScoreResponseDTOS.add(new YearScoreResponseDTO(yearScore.getId(), yearScore.getSchoolStudent().getId(), yearScore.getSchoolSubject(), yearScore.getScore()));
            }
        });

        return ResponseEntity.ok(yearScoreResponseDTOS);
    }

    @GetMapping("addYearScore")
    @ResponseBody
    public ResponseEntity<String> addYearScore(@RequestParam Long schoolStudentId, @RequestParam Long schoolSubjectId, @RequestParam Long score){
        YearScore yearScore = new YearScore();
        yearScore.setScore(score);
        yearScore.setSchoolSubject(scheduleService.findSchoolSubjectById(schoolSubjectId));
        yearScore.setSchoolStudent(schoolStudentService.findSchoolStudentById(schoolStudentId));

        yearScoreService.saveYearScore(yearScore);
        return ResponseEntity.ok("{\"message\": \"Оценка за год добавлена\"}");
    }

    @GetMapping("updateYearScore")
    @ResponseBody
    public ResponseEntity<String> updateYearScore(@RequestParam Long yearScoreId, @RequestParam Long score){
        YearScore yearScore = yearScoreService.findYearScoreById(yearScoreId);
        yearScore.setScore(score);
        yearScoreService.saveYearScore(yearScore);

        return ResponseEntity.ok("{\"message\": \"Годовая оценка обновлена\"}");
    }
}
