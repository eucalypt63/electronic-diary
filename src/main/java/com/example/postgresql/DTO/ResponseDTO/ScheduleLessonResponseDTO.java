package com.example.postgresql.DTO.ResponseDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ScheduleLessonResponseDTO {
    private Long id;
    private GroupResponseDTO group;
    private TeacherAssignmentResponseDTO teacherAssignment;
    private Long quarterNumber;
    private Long dayNumber;
    private Long lessonNumber;
}