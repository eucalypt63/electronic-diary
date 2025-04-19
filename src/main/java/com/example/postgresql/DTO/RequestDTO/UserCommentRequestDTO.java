package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class UserCommentRequestDTO {
    private Long id;
    private Long getterUserId;
    private Long senderUserId;
    private String content;
    private LocalDateTime dateTime;
}