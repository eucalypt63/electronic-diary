package com.example.postgresql.DTO.ResponseDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupResponseDTO {
    private Long id;
    private TeacherAssignmentResponseDTO teacherAssignment;
    private String groupName;
}