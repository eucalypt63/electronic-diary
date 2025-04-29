package com.example.postgresql.DTO.ResponseDTO.News;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class NewsResponseDTO {
    private Long id;

    private Long userId;
    private Long educationId;
    private String firstName;
    private String lastName;
    private String patronymic;

    private String title;
    private String content;
    private LocalDateTime dateTime;
}