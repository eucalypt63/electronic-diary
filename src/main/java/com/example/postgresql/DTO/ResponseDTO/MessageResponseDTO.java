package com.example.postgresql.DTO.ResponseDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MessageResponseDTO {
    private Long id;

    private Long getterUserId;
    private Long getterEducationId;
    private String getterFirstName;
    private String getterLastName;
    private String getterPatronymic;

    private Long senderUserId;
    private Long senderEducationId;
    private String senderFirstName;
    private String senderLastName;
    private String senderPatronymic;

    private String message;
    private LocalDateTime dateTime;
}