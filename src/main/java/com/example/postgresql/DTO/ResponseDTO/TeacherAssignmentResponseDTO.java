package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.TeacherResponseDTO;
import com.example.postgresql.model.SchoolSubject;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TeacherAssignmentResponseDTO {
    private Long id;
    private TeacherResponseDTO teacher;
    private SchoolSubject schoolSubject;
    private GroupResponseDTO group;
}