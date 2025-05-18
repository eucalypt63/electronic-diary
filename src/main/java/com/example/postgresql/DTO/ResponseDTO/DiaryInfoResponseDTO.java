package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.SchoolSubject;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class DiaryInfoResponseDTO {
    private SchoolSubject schoolSubject;
    private LocalDateTime dateTime;
    private boolean Attendance;
    private Long score;
}