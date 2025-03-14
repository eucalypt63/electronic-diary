package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudentParentRequestDTO {
    Long schoolStudentId;
    Long parentId;
    Long parentTypeId;
}
