package com.example.postgresql.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudentParentDTO {
    Long schoolStudentId;
    Long parentId;
    Long parentTypeId;
}
