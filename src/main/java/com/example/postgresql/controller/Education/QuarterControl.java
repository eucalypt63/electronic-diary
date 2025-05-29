package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.ResponseDTO.QuarterScoreResponseDTO;
import com.example.postgresql.model.Education.Gradebook.QuarterScore;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Education.Notification;
import com.example.postgresql.model.SchoolSubject;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.service.Education.GroupService;
import com.example.postgresql.service.Education.NotificationService;
import com.example.postgresql.service.Education.QuarterScoreService;
import com.example.postgresql.service.Education.ScheduleService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuarterControl {
    @Autowired
    private QuarterScoreService quarterScoreService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private NotificationService notificationService;

    // Получение четвертных оценок для журнала
    @GetMapping("findQuarterScoreByTeacherAssignmentIdAndQuarterInfoId")
    @ResponseBody
    public ResponseEntity<List<QuarterScoreResponseDTO>> findGradebookDayByScheduleLessonTeacherAssignmentId(@RequestParam Long teacherAssignmentId, @RequestParam Long quarterId){
        TeacherAssignment teacherAssignment = teacherService.findTeacherAssignmentById(teacherAssignmentId);
        List<GroupMember> groupMembers = groupService.findGroupMemberByGroupId(teacherAssignment.getGroup().getId());

        List<QuarterScoreResponseDTO> quarterScoreResponseDTOS = new ArrayList<>();
        groupMembers.forEach(groupMember -> {
            QuarterScore quarterScore = quarterScoreService.findQuarterScoreBySchoolStudentIdAndSchoolSubjectIdAndQuarterInfoId(groupMember.getSchoolStudent().getId(), teacherAssignment.getSchoolSubject().getId(), quarterId);

            if (quarterScore != null) {
                quarterScoreResponseDTOS.add(new QuarterScoreResponseDTO(quarterScore.getId(), quarterScore.getQuarterInfo().getQuarterNumber(), quarterScore.getSchoolStudent().getId(), quarterScore.getSchoolSubject(), quarterScore.getScore()));
            }
        });

        return ResponseEntity.ok(quarterScoreResponseDTOS);
    }

    // Получение четвертных оценок для годовых оценок группы по предмету
    @GetMapping("findQuarterScoreByTeacherAssignmentId")
    @ResponseBody
    public ResponseEntity<List<QuarterScoreResponseDTO>> findQuarterScoreByTeacherAssignmentId(@RequestParam Long teacherAssignmentId){
        TeacherAssignment teacherAssignment = teacherService.findTeacherAssignmentById(teacherAssignmentId);
        List<GroupMember> groupMembers = groupService.findGroupMemberByGroupId(teacherAssignment.getGroup().getId());

        List<QuarterScoreResponseDTO> quarterScoreResponseDTOS = new ArrayList<>();
        groupMembers.forEach(groupMember -> {
            List<QuarterScore> quarterScores = quarterScoreService.findQuarterScoreBySchoolStudentIdAndSchoolSubjectId(groupMember.getSchoolStudent().getId(), teacherAssignment.getSchoolSubject().getId());

            if (!quarterScores.isEmpty()) {
                quarterScores.forEach(quarterScore -> {
                    quarterScoreResponseDTOS.add(new QuarterScoreResponseDTO(quarterScore.getId(), quarterScore.getQuarterInfo().getQuarterNumber(), quarterScore.getSchoolStudent().getId(), quarterScore.getSchoolSubject(), quarterScore.getScore()));
                });
            }
        });

        return ResponseEntity.ok(quarterScoreResponseDTOS);
    }

    // Получение четвертных оценок для дневника
    @GetMapping("findQuarterScoreBySchoolStudentId")
    @ResponseBody
    public ResponseEntity<List<QuarterScoreResponseDTO>> findQuarterScoreBySchoolStudentId(@RequestParam Long schoolStudentId){
        List<QuarterScore> quarterScores = quarterScoreService.findQuarterScoreBySchoolStudentId(schoolStudentId);
        List<QuarterScoreResponseDTO> quarterScoreResponseDTOS = new ArrayList<>();
        quarterScores.forEach(quarterScore -> {
            quarterScoreResponseDTOS.add(new QuarterScoreResponseDTO(quarterScore.getId(), quarterScore.getQuarterInfo().getQuarterNumber(), quarterScore.getSchoolStudent().getId(), quarterScore.getSchoolSubject(), quarterScore.getScore()));
        });

        return ResponseEntity.ok(quarterScoreResponseDTOS);
    }

    @GetMapping("addQuarterScore")
    @ResponseBody
    public ResponseEntity<String> addQuarterScore(@RequestParam Long schoolStudentId, @RequestParam Long schoolSubjectId, @RequestParam Long quarterId, @RequestParam Long score){
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(schoolStudentId);
        SchoolSubject schoolSubject = scheduleService.findSchoolSubjectById(schoolSubjectId);

        QuarterScore quarterScore = new QuarterScore();
        quarterScore.setScore(score);
        quarterScore.setQuarterInfo(scheduleService.findQuarterInfoByQuarterNumber(quarterId));
        quarterScore.setSchoolSubject(schoolSubject);
        quarterScore.setSchoolStudent(schoolStudent);
        quarterScoreService.saveQuarterScore(quarterScore);

        Notification notification = new Notification();
        notification.setUser(schoolStudent.getUser());
        notification.setLink(
                String.format(
                        "/schoolStudentQuarterResult?id=%d",
                        schoolStudent.getId()
                )
        );
        notification.setDateTime(LocalDateTime.now());
        notification.setTitle("Успеваемость");
        notification.setContent(
                String.format(
                        "Вам была поставлена оценка %d за %d четверть по предмету \"%s\"",
                        score,
                        quarterScore.getQuarterInfo().getQuarterNumber(),
                        schoolSubject.getName()
                )
        );
        notificationService.saveNotification(notification);

        return ResponseEntity.ok("{\"message\": \"Оценка за четверть добавлена\"}");
    }

    @GetMapping("updateQuarterScore")
    @ResponseBody
    public ResponseEntity<String> updateQuarterScore(@RequestParam Long quarterScoreId, @RequestParam Long score){
        QuarterScore quarterScore = quarterScoreService.findQuarterScoreById(quarterScoreId);
        quarterScore.setScore(score);
        quarterScoreService.saveQuarterScore(quarterScore);

        Notification notification = new Notification();
        notification.setUser(quarterScore.getSchoolStudent().getUser());
        notification.setLink(
                String.format(
                        "/schoolStudentQuarterResult?id=%d",
                        quarterScore.getSchoolStudent().getId()
                )
        );
        notification.setDateTime(LocalDateTime.now());
        notification.setTitle("Успеваемость");
        notification.setContent(
                String.format(
                        "Ваша оценка за %d четверть по предмету \"%s\" была заменена на %d",
                        quarterScore.getQuarterInfo().getQuarterNumber(),
                        quarterScore.getSchoolSubject().getName(),
                        score
                )
        );
        notificationService.saveNotification(notification);

        return ResponseEntity.ok("{\"message\": \"Оценка за четверть обновлена\"}");
    }
}
