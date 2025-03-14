package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.Users.Education.EducationalInstitution;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClassResponseDTO {
    private Long id;
    private String name;
    private TeacherResponseDTO teacher;
}