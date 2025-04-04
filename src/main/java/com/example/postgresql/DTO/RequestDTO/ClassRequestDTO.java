package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClassRequestDTO {
    private Long id;
    private String name;
    private Long teacherId;
}