package com.example.postgresql.DTO.ResponseDTO;

import com.example.postgresql.model.SchoolSubject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearScoreResponseDTO {
    private Long id;
    private Long schoolStudentId;
    private SchoolSubject schoolSubject;
    private Long score;
}