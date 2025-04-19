package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MessageRequestDTO {
    private Long id;
    private Long getterUserId;
    private Long senderUserId;
    private String message;
    private LocalDateTime dateTime;
}