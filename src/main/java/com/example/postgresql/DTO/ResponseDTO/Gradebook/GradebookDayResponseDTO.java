package com.example.postgresql.DTO.ResponseDTO.Gradebook;

import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class GradebookDayResponseDTO {
    private Long id;
    private ScheduleLessonResponseDTO scheduleLesson;
    private LocalDateTime dateTime;
    private String topic;
    private String homework;
}