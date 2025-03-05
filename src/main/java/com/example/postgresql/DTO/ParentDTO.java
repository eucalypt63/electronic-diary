package com.example.postgresql.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParentDTO {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;

    private Long parentType;
    private Long schoolStudentId;
}