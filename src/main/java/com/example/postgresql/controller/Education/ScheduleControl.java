package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.ResponseDTO.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.ScheduleLessonResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.SchoolStudentResponseDTO;
import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.GroupService;
import com.example.postgresql.service.Education.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScheduleControl {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("/findLessonsByClassId")
    @ResponseBody
    public ResponseEntity<Map<Long, List<ScheduleLessonResponseDTO>>> findLessonsByClassId (Long id, Long quarter){
        List<Group> groups = groupService.findGroupByClassId(id);
        Map<Long, List<ScheduleLessonResponseDTO>> scheduleMap = new HashMap<>();
        List<ScheduleLessonResponseDTO> lessonsDTO = new ArrayList<>();

        for (Group group : groups) {
            List<ScheduleLesson> lessons = scheduleService.findScheduleLessonByGroupIdAndQuarterNumber(group.getId(), quarter);

            for (ScheduleLesson scheduleLesson : lessons){
                lessonsDTO.add(dtoService.ScheduleLessonToDto(scheduleLesson));
            }
            scheduleMap.put(group.getId(), lessonsDTO);
        }

        return ResponseEntity.ok(scheduleMap);
    }
}
