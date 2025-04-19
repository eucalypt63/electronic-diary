package com.example.postgresql.DTO.ResponseDTO.News;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class NewsCommentResponseDTO {
    private Long id;

    private Long userId;
    private Long educationId;
    private String firstName;
    private String lastName;
    private String patronymic;

    private NewsResponseDTO newsResponseDTO;

    private String content;
    private LocalDateTime dateTime;
}