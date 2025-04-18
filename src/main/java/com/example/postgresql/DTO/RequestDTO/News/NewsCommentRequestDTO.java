package com.example.postgresql.DTO.RequestDTO.News;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class NewsCommentRequestDTO {
    private Long id;
    private Long userId;
    private Long newsId;
    private String content;
    private LocalDateTime dateTime;
}