package com.example.postgresql.DTO.ResponseDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupMemberResponseDTO {
    private Long id;
    private GroupResponseDTO group;
    private SchoolStudentResponseDTO schoolStudent;
}