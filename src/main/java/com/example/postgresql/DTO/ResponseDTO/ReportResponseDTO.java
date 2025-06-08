package com.example.postgresql.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReportResponseDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String downloadUrl;
}