package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.ResponseDTO.YearScoreResponseDTO;
import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Education.Gradebook.YearScore;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Education.Notification;
import com.example.postgresql.model.SchoolSubject;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.service.Education.*;
import com.example.postgresql.service.Users.ParentService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ParentService parentService;

    // Получение годовых оценок для ученика
    @GetMapping("findYearScoreBySchoolStudentId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<YearScoreResponseDTO>> findYearScoreBySchoolStudentId(@RequestParam Long schoolStudentId){
        List<YearScore> yearScores = yearScoreService.findYearScoreBySchoolStudentId(schoolStudentId);
        List<YearScoreResponseDTO> yearScoreResponseDTOS = new ArrayList<>();
        yearScores.forEach(yearScore -> {
            yearScoreResponseDTOS.add(new YearScoreResponseDTO(yearScore.getId(), schoolStudentId, yearScore.getSchoolSubject(), yearScore.getScore()));
        });

        return ResponseEntity.ok(yearScoreResponseDTOS);
    }

    // Получение годовых оценок группы по предмету
    @GetMapping("findYearScoreByTeacherAssignmentId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
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

    @PostMapping("addYearScore")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> addYearScore(@RequestParam Long schoolStudentId, @RequestParam Long schoolSubjectId, @RequestParam Long score){
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(schoolStudentId);
        SchoolSubject schoolSubject = scheduleService.findSchoolSubjectById(schoolSubjectId);
        YearScore yearScore = new YearScore();
        yearScore.setScore(score);
        yearScore.setSchoolSubject(schoolSubject);
        yearScore.setSchoolStudent(schoolStudent);

        yearScoreService.saveYearScore(yearScore);

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
                        "Вам была поставлена оценка за год %d по предмету \"%s\"",
                        score,
                        schoolSubject.getName()
                )
        );
        notificationService.saveNotification(notification);

        List<StudentParent> studentParents = parentService.findStudentParentBySchoolStudentId(schoolStudentId);
        studentParents.forEach(studentParent -> {
            Notification notificationParent = new Notification();
            notificationParent.setUser(studentParent.getParent().getUser());
            notificationParent.setLink(
                    String.format(
                            "/schoolStudentQuarterResult?id=%d",
                            schoolStudent.getId()
                    )
            );
            notificationParent.setDateTime(LocalDateTime.now());
            notificationParent.setTitle("Успеваемость");
            notificationParent.setContent(
                    String.format(
                            "Ученику %s %s была поставлена оценка за год %d по предмету \"%s\"",
                            schoolStudent.getLastName(),
                            schoolStudent.getFirstName(),
                            score,
                            schoolSubject.getName()
                    )
            );
            notificationService.saveNotification(notificationParent);
        });

        return ResponseEntity.ok("{\"message\": \"Оценка за год добавлена\"}");
    }

    @PostMapping("updateYearScore")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> updateYearScore(@RequestParam Long yearScoreId, @RequestParam Long score){
        YearScore yearScore = yearScoreService.findYearScoreById(yearScoreId);
        yearScore.setScore(score);
        yearScoreService.saveYearScore(yearScore);

        SchoolStudent schoolStudent = yearScore.getSchoolStudent();

        Notification notification = new Notification();
        notification.setUser(schoolStudent.getUser());
        notification.setLink(
                String.format(
                        "/schoolStudentQuarterResult?id=%d",
                        yearScore.getSchoolStudent().getId()
                )
        );
        notification.setDateTime(LocalDateTime.now());
        notification.setTitle("Успеваемость");
        notification.setContent(
                String.format(
                        "Ваша оценка за год по предмету \"%s\" была заменена на %d",
                        yearScore.getSchoolSubject().getName(),
                        score
                )
        );
        notificationService.saveNotification(notification);

        List<StudentParent> studentParents = parentService.findStudentParentBySchoolStudentId(schoolStudent.getId());
        studentParents.forEach(studentParent -> {
            Notification notificationParent = new Notification();
            notificationParent.setUser(studentParent.getParent().getUser());
            notificationParent.setLink(
                    String.format(
                            "/schoolStudentQuarterResult?id=%d",
                            schoolStudent.getId()
                    )
            );
            notificationParent.setDateTime(LocalDateTime.now());
            notificationParent.setTitle("Успеваемость");
            notificationParent.setContent(
                    String.format(
                            "Оценка за год ученика %s %s по предмету \"%s\" была заменена на %d",
                            schoolStudent.getLastName(),
                            schoolStudent.getFirstName(),
                            yearScore.getSchoolSubject().getName(),
                            score
                    )
            );
            notificationService.saveNotification(notificationParent);
        });

        return ResponseEntity.ok("{\"message\": \"Годовая оценка обновлена\"}");
    }
}
