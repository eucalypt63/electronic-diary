package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.DTO.ResponseDTO.Users.TeacherResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class NotificationResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String link;
    private LocalDateTime dateTime;
}