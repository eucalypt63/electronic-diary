package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.Education.Gradebook.QuarterInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ScheduleLessonsDayResponseDTO {
    private Long id;
    private GroupResponseDTO group;
    private TeacherAssignmentResponseDTO teacherAssignment;
}