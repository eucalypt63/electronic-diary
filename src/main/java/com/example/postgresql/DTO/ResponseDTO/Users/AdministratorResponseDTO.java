package com.example.postgresql.DTO.ResponseDTO.Users;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.Administrations.AdministrationsTypes;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AdministratorResponseDTO {
    private Long id;
    private Long userid;
    private EducationalInstitution educationalInstitution;
    private AdministrationsTypes administrationsTypes;

    private String firstName;
    private String lastName;
    private String patronymic;

    private String pathImage;
    private String email;
    private String phoneNumber;
}