package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudentParentRequestDTO {
    private Long id;
    private Long schoolStudentId;
    private Long parentId;
    private Long parentTypeId;
}
