package com.example.postgresql.DTO.ResponseDTO.Schedule;

import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.TeacherAssignmentResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ScheduleLessonsDayResponseDTO {
    private Long id;
    private GroupResponseDTO group;
    private TeacherAssignmentResponseDTO teacherAssignment;
}