package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.User.UserType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthorizationUserResponseDTO {
    private Long id;
    private String role;
}