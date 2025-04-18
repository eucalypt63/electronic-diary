package com.example.postgresql.DTO.ResponseDTO.Schedule;

import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.TeacherAssignmentResponseDTO;
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