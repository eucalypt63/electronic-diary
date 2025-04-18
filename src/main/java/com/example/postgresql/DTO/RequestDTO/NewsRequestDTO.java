package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class NewsRequestDTO {
    private Long id;
    private Long educationalInstitutionId;
    private Long ownerUserId;
    private String title;
    private String content;
    private LocalDateTime dateTime;
}