package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.Users.Education.EducationalInstitution;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AdministratorResponseDTO {
    private Long id;
    private EducationalInstitution educationalInstitution;

    private String firstName;
    private String lastName;
    private String patronymic;

    private String pathImage;
    private String email;
    private String phoneNumber;
}