package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.DTO.ResponseDTO.News.NewsResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class UserCommentResponseDTO {
    private Long id;

    private Long getterUserId;
    private String getterFirstName;
    private String getterLastName;
    private String getterPatronymic;

    private Long senderUserId;
    private String senderFirstName;
    private String senderLastName;
    private String senderPatronymic;

    private String content;
    private LocalDateTime dateTime;
}