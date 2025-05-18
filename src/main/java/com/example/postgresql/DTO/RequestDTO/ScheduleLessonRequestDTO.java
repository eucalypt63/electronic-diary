package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ScheduleLessonRequestDTO {
    private Long quarter;
    private Long dayNumber;
    private Long lessonNumber;
    private Long groupId;
    private Long subjectId;
    private Long teacherId;
    private String room;
}