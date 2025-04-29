package com.example.postgresql.DTO.ResponseDTO.Gradebook;

import com.example.postgresql.DTO.ResponseDTO.Users.SchoolStudentResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GradebookScoreResponseDTO {
    private Long id;
    private GradebookDayResponseDTO gradebookDay;
    private SchoolStudentResponseDTO schoolStudent;
    private Long score;
}