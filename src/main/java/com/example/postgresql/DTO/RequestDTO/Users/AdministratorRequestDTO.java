package com.example.postgresql.DTO.RequestDTO.Users;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AdministratorRequestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;
    private Long universityId;
    private Long administrationsTypeId;
}