package com.example.postgresql.DTO.ResponseDTO.Users;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SchoolStudentResponseDTO {
    private Long id;
    private Long userid;
    private Class classRoom;
    private EducationalInstitution educationalInstitution;

    private String firstName;
    private String lastName;
    private String patronymic;

    private String pathImage;
    private String email;
    private String phoneNumber;
}