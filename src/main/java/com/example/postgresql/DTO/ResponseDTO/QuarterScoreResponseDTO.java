package com.example.postgresql.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuarterScoreResponseDTO {
    private Long id;
    private Long schoolStudentId;
    private Long score;
}