package com.example.postgresql.DTO.ResponseDTO.Group;

import com.example.postgresql.DTO.ResponseDTO.Users.SchoolStudentResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupMemberResponseDTO {
    private Long id;
    private GroupResponseDTO group;
    private SchoolStudentResponseDTO schoolStudent;
}