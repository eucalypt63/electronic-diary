package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.Users.Student.ParentType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudentParentResponseDTO {
    private Long id;
    private SchoolStudentResponseDTO schoolStudent;
    private ParentResponseDTO parent;
    private ParentType parentType;
}