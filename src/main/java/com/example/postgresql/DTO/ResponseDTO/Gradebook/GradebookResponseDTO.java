package com.example.postgresql.DTO.ResponseDTO.Gradebook;

import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonResponseDTO;
import com.example.postgresql.model.Education.Gradebook.GradebookDay;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GradebookResponseDTO {
    private List<GradebookDayResponseDTO> gradebookDayResponseDTOS;
    private List<GradebookAttendanceResponseDTO> gradebookAttendanceResponseDTOS;
    private List<GradebookScoreResponseDTO> gradebookScoreResponseDTOS;
}