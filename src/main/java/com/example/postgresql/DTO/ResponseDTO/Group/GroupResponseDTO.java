package com.example.postgresql.DTO.ResponseDTO.Group;

import com.example.postgresql.DTO.ResponseDTO.ClassResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupResponseDTO {
    private Long id;
    private ClassResponseDTO classRoom;
    private String groupName;
}