package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.Education.Gradebook.QuarterInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ScheduleLessonResponseDTO {
    private Long id;
    private GroupResponseDTO group;
    private TeacherAssignmentResponseDTO teacherAssignment;
    private QuarterInfo quarterInfo;
    private Long dayNumber;
    private Long lessonNumber;
}