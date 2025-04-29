package com.example.postgresql.DTO.ResponseDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MessageResponseDTO {
    private Long id;

    private Long getterUserId;
    private String getterFirstName;
    private String getterLastName;
    private String getterPatronymic;

    private Long senderUserId;
    private String senderFirstName;
    private String senderLastName;
    private String senderPatronymic;

    private String message;
    private LocalDateTime dateTime;
}