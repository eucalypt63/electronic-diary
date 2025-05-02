package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.ScheduleLessonRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Schedule.LessonParamsResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonsDayResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.TeacherResponseDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Education.Gradebook.GradebookDay;
import com.example.postgresql.model.Education.Gradebook.QuarterInfo;
import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.*;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ScheduleControl {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;
    @Autowired
    private ClassService classService;
    @Autowired
    private GradebookService gradebookService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("/findLessonsByClassId")
    @ResponseBody
    public ResponseEntity<Map<Long, List<ScheduleLessonResponseDTO>>> findLessonsByClassId (@RequestParam Long id, @RequestParam Long quarter){
        List<Group> groups = groupService.findGroupByClassId(id);
        Map<Long, List<ScheduleLessonResponseDTO>> scheduleMap = new HashMap<>();
        List<ScheduleLessonResponseDTO> lessonsDTO = new ArrayList<>();

        for (Group group : groups) {
            List<ScheduleLesson> lessons = scheduleService.findScheduleLessonByGroupIdAndQuarterNumber(group.getId(), quarter);

            for (ScheduleLesson scheduleLesson : lessons){
                System.out.println(lessons);
                lessonsDTO.add(dtoService.ScheduleLessonToDto(scheduleLesson));
            }

            scheduleMap.put(group.getId(), lessonsDTO);
        }

        return ResponseEntity.ok(scheduleMap);
    }

    @GetMapping("/findLessonsBySchoolStudentId")
    @ResponseBody
    public ResponseEntity<Map<Long, List<ScheduleLessonResponseDTO>>> findLessonsBySchoolStudentId (@RequestParam Long id, @RequestParam Long quarter){
        List<GroupMember> groupMembers = groupService.findGroupMemberBySchoolStudentId(id);

        List<Group> groups = groupMembers.stream()
                .map(GroupMember::getGroup)
                .distinct()
                .toList();

        Map<Long, List<ScheduleLessonResponseDTO>> scheduleMap = new HashMap<>();
        List<ScheduleLessonResponseDTO> lessonsDTO = new ArrayList<>();

        for (Group group : groups) {
            List<ScheduleLesson> lessons = scheduleService.findScheduleLessonByGroupIdAndQuarterNumber(group.getId(), quarter);

            for (ScheduleLesson scheduleLesson : lessons){
                System.out.println(lessons);
                lessonsDTO.add(dtoService.ScheduleLessonToDto(scheduleLesson));
            }

            scheduleMap.put(group.getId(), lessonsDTO);
        }

        return ResponseEntity.ok(scheduleMap);
    }

    @GetMapping("/findLessonsByTeacherId")
    @ResponseBody
    public ResponseEntity<Map<Long, List<ScheduleLessonResponseDTO>>> findLessonsByLessonNumberAndTeacherId(@RequestParam Long id, @RequestParam Long quarter) {

        List<TeacherAssignment> teacherAssignments = teacherService.findTeacherAssignmentByTeacherId(id);
        Map<Long, List<ScheduleLessonResponseDTO>> scheduleMap = new HashMap<>();
        List<ScheduleLessonResponseDTO> lessonsDTO = new ArrayList<>();

        teacherAssignments.forEach(teacherAssignment -> {
            List<ScheduleLesson> lessons = scheduleService.findScheduleLessonByTeacherAssignmentIdAndQuarterNumber(teacherAssignment.getId(), quarter);

            for (ScheduleLesson scheduleLesson : lessons){
                System.out.println(lessons);
                lessonsDTO.add(dtoService.ScheduleLessonToDto(scheduleLesson));
            }

            scheduleMap.put(teacherAssignment.getId(), lessonsDTO);
        });


        return ResponseEntity.ok(scheduleMap);
    }

    //Добавить даты для журнала
    @GetMapping("getLessonAddParams")
    @ResponseBody
    public ResponseEntity<LessonParamsResponseDTO> getLessonAddParams(Long id){
        LessonParamsResponseDTO lessonParamsResponseDTO = new LessonParamsResponseDTO();
        Class cl = classService.findClassById(id);
        EducationalInstitution educationalInstitution = cl.getTeacher().getEducationalInstitution();

        List<Group> groups = groupService.findGroupByClassId(id);
        List<GroupResponseDTO> groupResponseDTOS = new ArrayList<>();
        for (Group group : groups) {
            groupResponseDTOS.add(dtoService.GroupToDto(group));
        }
        lessonParamsResponseDTO.setGroups(groupResponseDTOS);

        List<Teacher> teachers = teacherService.findTeacherByEducationalInstitutionId(educationalInstitution.getId());
        List<TeacherResponseDTO> teacherResponseDTOS = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherResponseDTOS.add(dtoService.TeacherToDto(teacher));
        }
        lessonParamsResponseDTO.setTeachers(teacherResponseDTOS);

        lessonParamsResponseDTO.setSchoolSubjects(scheduleService.getAllSchoolSubject());

        return  ResponseEntity.ok(lessonParamsResponseDTO);
    }

    //Исправить
    @GetMapping("getLessonAddParamsBySchoolStudentId")
    @ResponseBody
    public ResponseEntity<LessonParamsResponseDTO> getLessonAddParamsBySchoolStudentId(Long id){
        LessonParamsResponseDTO lessonParamsResponseDTO = new LessonParamsResponseDTO();
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);

        Class cl = classService.findClassById(schoolStudent.getClassRoom().getId());
        EducationalInstitution educationalInstitution = cl.getTeacher().getEducationalInstitution();

        List<Group> groups = groupService.findGroupByClassId(schoolStudent.getClassRoom().getId());
        List<GroupResponseDTO> groupResponseDTOS = new ArrayList<>();
        for (Group group : groups) {
            groupResponseDTOS.add(dtoService.GroupToDto(group));
        }
        lessonParamsResponseDTO.setGroups(groupResponseDTOS);

        List<Teacher> teachers = teacherService.findTeacherByEducationalInstitutionId(educationalInstitution.getId());
        List<TeacherResponseDTO> teacherResponseDTOS = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherResponseDTOS.add(dtoService.TeacherToDto(teacher));
        }
        lessonParamsResponseDTO.setTeachers(teacherResponseDTOS);

        lessonParamsResponseDTO.setSchoolSubjects(scheduleService.getAllSchoolSubject());

        return  ResponseEntity.ok(lessonParamsResponseDTO);
    }

    @GetMapping("getLessonAddParamsByTeacherId")
    @ResponseBody
    public ResponseEntity<LessonParamsResponseDTO> getLessonAddParamsByTeacherId(Long id){
        LessonParamsResponseDTO lessonParamsResponseDTO = new LessonParamsResponseDTO();
        Teacher teacher = teacherService.findTeacherById(id);

        List<TeacherAssignment> teacherAssignments = teacherService.findTeacherAssignmentByTeacherId(id);
        List<Group> groups = teacherAssignments.stream()
                .map(TeacherAssignment::getGroup)
                .toList();

        List<GroupResponseDTO> groupResponseDTOS = new ArrayList<>();
        for (Group group : groups) {
            groupResponseDTOS.add(dtoService.GroupToDto(group));
        }
        lessonParamsResponseDTO.setGroups(groupResponseDTOS);

        List<TeacherResponseDTO> teacherResponseDTOS = new ArrayList<>();
        teacherResponseDTOS.add(dtoService.TeacherToDto(teacher));

        lessonParamsResponseDTO.setTeachers(teacherResponseDTOS);

        lessonParamsResponseDTO.setSchoolSubjects(scheduleService.getAllSchoolSubject());

        return  ResponseEntity.ok(lessonParamsResponseDTO);
    }

    @PostMapping("/addLesson")
    @ResponseBody
    public ResponseEntity<String> addLesson (@RequestBody ScheduleLessonRequestDTO schLesReqDTO) {

        if (scheduleService.checkAvailability(schLesReqDTO.getQuarter(), schLesReqDTO.getDayNumber(),
                                                schLesReqDTO.getLessonNumber(),schLesReqDTO.getTeacherId())){

            TeacherAssignment teacherAssignment = teacherService.findTeacherAssignmentByGroupIdAndSchoolSubjectIdAndTeacherId(
                    schLesReqDTO.getGroupId(),
                    schLesReqDTO.getSubjectId(),
                    schLesReqDTO.getTeacherId()
            );
            ScheduleLesson scheduleLesson = new ScheduleLesson();
            if (teacherAssignment != null){
                scheduleLesson.setTeacherAssignment(teacherAssignment);
            }
            else {
                TeacherAssignment newTeacherAssignment = new TeacherAssignment();
                newTeacherAssignment.setTeacher(teacherService.findTeacherById(schLesReqDTO.getTeacherId()));
                newTeacherAssignment.setSchoolSubject(scheduleService.findSchoolSubjectById(schLesReqDTO.getSubjectId()));
                newTeacherAssignment.setGroup(groupService.findGroupById(schLesReqDTO.getGroupId()));
                teacherService.saveTeacherAssignment(newTeacherAssignment);

                scheduleLesson.setTeacherAssignment(newTeacherAssignment);
            }
            QuarterInfo quarterInfo = scheduleService.findQuarterInfoByQuarterNumber(schLesReqDTO.getQuarter());

            scheduleLesson.setGroup(groupService.findGroupById(schLesReqDTO.getGroupId()));
            scheduleLesson.setQuarterInfo(quarterInfo);
            scheduleLesson.setDayNumber(schLesReqDTO.getDayNumber());
            scheduleLesson.setLessonNumber(schLesReqDTO.getLessonNumber());
            scheduleService.saveScheduleLesson(scheduleLesson);

            createGradebookDaysForQuarter(scheduleLesson, quarterInfo, schLesReqDTO.getDayNumber());

            return ResponseEntity.ok("{\"message\": \"Урок успешно добавлен\"}");
        } else {return ResponseEntity.status(500).body("{\"message\": \"Время занято для учителя \"}");}
    }

    private void createGradebookDaysForQuarter(ScheduleLesson scheduleLesson, QuarterInfo quarterInfo, Long dayNumber) {
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int targetYear;

        if (quarterInfo.getQuarterNumber() <= 2) {
            targetYear = (now.getMonthValue() <= 8) ? currentYear - 1 : currentYear;
        } else {
            targetYear = currentYear;
        }

        LocalDateTime startDate = quarterInfo.getDateStartTime()
                .withYear(targetYear);
        LocalDateTime endDate = quarterInfo.getDateEndTime()
                .withYear(targetYear);

        DayOfWeek targetDayOfWeek = DayOfWeek.of(dayNumber.intValue());

        LocalDateTime currentDate = startDate;
        while (currentDate.getDayOfWeek() != targetDayOfWeek) {
            currentDate = currentDate.plusDays(1);
        }

        while (!currentDate.isAfter(endDate)) {
            GradebookDay gradebookDay = new GradebookDay();
            gradebookDay.setScheduleLesson(scheduleLesson);
            gradebookDay.setDateTime(currentDate);
            gradebookService.saveGradebookDay(gradebookDay);

            currentDate = currentDate.plusWeeks(1);
        }
    }

    @GetMapping("/findLessonsByLessonNumber")
    @ResponseBody
    public ResponseEntity<Map<Long, ScheduleLessonsDayResponseDTO>> findLessonsByLessonNumber(
            @RequestParam Long id,
            @RequestParam int day,
            @RequestParam int lessonNumber,
            @RequestParam int quarter) {

        List<ScheduleLesson> lessons = scheduleService.findScheduleLessonsByClassAndTime(
                id, day, lessonNumber, quarter);

        Map<Long, ScheduleLessonsDayResponseDTO> result = lessons.stream()
                .collect(Collectors.toMap(
                        ScheduleLesson::getId,
                        lesson -> {
                            ScheduleLessonsDayResponseDTO dto = new ScheduleLessonsDayResponseDTO();
                            dto.setId(lesson.getId());
                            dto.setGroup(dtoService.GroupToDto(lesson.getGroup()));
                            dto.setTeacherAssignment(dtoService.TeacherAssignmentToDto(lesson.getTeacherAssignment()));
                            return dto;
                        }
                ));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/findLessonsByLessonNumberAndSchoolStudentId")
    @ResponseBody
    public ResponseEntity<Map<Long, ScheduleLessonsDayResponseDTO>> findLessonsByLessonNumberAndSchoolStudentId(
            @RequestParam Long id,
            @RequestParam int day,
            @RequestParam int lessonNumber,
            @RequestParam int quarter) {

        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(id);
        List<GroupMember> groupMembers = groupService.findGroupMemberBySchoolStudentId(id);

        List<Group> groups = groupMembers.stream()
                .map(GroupMember::getGroup)
                .distinct()
                .toList();

        List<ScheduleLesson> lessons = scheduleService.findScheduleLessonsByClassAndTime(
                schoolStudent.getClassRoom().getId(), day, lessonNumber, quarter);

        List<ScheduleLesson> filteredLessons = lessons.stream()
                .filter(lesson -> groups.contains(lesson.getGroup()))
                .toList();

        Map<Long, ScheduleLessonsDayResponseDTO> result = filteredLessons.stream()
                .collect(Collectors.toMap(
                        ScheduleLesson::getId,
                        lesson -> {
                            ScheduleLessonsDayResponseDTO dto = new ScheduleLessonsDayResponseDTO();
                            dto.setId(lesson.getId());
                            dto.setGroup(dtoService.GroupToDto(lesson.getGroup()));
                            dto.setTeacherAssignment(dtoService.TeacherAssignmentToDto(lesson.getTeacherAssignment()));
                            return dto;
                        }
                ));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/findLessonsByLessonNumberAndTeacherId")
    @ResponseBody
    public ResponseEntity<Map<Long, ScheduleLessonsDayResponseDTO>> findLessonsByLessonNumberAndTeacherId(
            @RequestParam Long id,
            @RequestParam int day,
            @RequestParam int lessonNumber,
            @RequestParam int quarter) {

        List<TeacherAssignment> teacherAssignments = teacherService.findTeacherAssignmentByTeacherId(id);

        List<ScheduleLesson> lessons = new ArrayList<>();
        teacherAssignments.forEach(teacherAssignment -> {
            lessons.addAll(scheduleService.findScheduleLessonsByTeacherAssignmentIdAndTime(
                    teacherAssignment.getId(), day, lessonNumber, quarter));
        });

        Map<Long, ScheduleLessonsDayResponseDTO> result = lessons.stream()
                .collect(Collectors.toMap(
                        ScheduleLesson::getId,
                        lesson -> {
                            ScheduleLessonsDayResponseDTO dto = new ScheduleLessonsDayResponseDTO();
                            dto.setId(lesson.getId());
                            dto.setGroup(dtoService.GroupToDto(lesson.getGroup()));
                            dto.setTeacherAssignment(dtoService.TeacherAssignmentToDto(lesson.getTeacherAssignment()));
                            return dto;
                        }
                ));

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteLesson")
    public ResponseEntity<String> deleteLesson(@RequestParam Long id) {
        scheduleService.deleteScheduleLesson(id);
        return ResponseEntity.ok("{\"message\": \"Урок успешно удалён\"}");
    }

    @PutMapping("/updateLesson")
    public ResponseEntity<String> updateLesson(@RequestBody ScheduleLessonRequestDTO requestDTO) {
        ScheduleLesson scheduleLesson = scheduleService.findScheduleLessonById(requestDTO.getLessonNumber());
        scheduleLesson.setGroup(groupService.findGroupById(requestDTO.getGroupId()));

        TeacherAssignment teacherAssignment = teacherService.findTeacherAssignmentByGroupIdAndSchoolSubjectIdAndTeacherId(requestDTO.getGroupId(),
                                                                                    requestDTO.getSubjectId(),
                                                                                    requestDTO.getTeacherId());
        if (teacherAssignment != null){
            scheduleLesson.setTeacherAssignment(teacherAssignment);
        }
        else {
            TeacherAssignment newTeacherAssignment = new TeacherAssignment();
            newTeacherAssignment.setTeacher(teacherService.findTeacherById(requestDTO.getTeacherId()));
            newTeacherAssignment.setSchoolSubject(scheduleService.findSchoolSubjectById(requestDTO.getSubjectId()));
            newTeacherAssignment.setGroup(groupService.findGroupById(requestDTO.getGroupId()));
            teacherService.saveTeacherAssignment(newTeacherAssignment);

            scheduleLesson.setTeacherAssignment(newTeacherAssignment);
        }
        scheduleService.saveScheduleLesson(scheduleLesson);
        return ResponseEntity.ok("{\"message\": \"Урок успешно обновлён\"}");
    }
}
