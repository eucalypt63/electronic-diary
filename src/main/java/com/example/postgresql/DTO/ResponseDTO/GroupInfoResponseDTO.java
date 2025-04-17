package com.example.postgresql.DTO.ResponseDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class GroupInfoResponseDTO {
    private GroupResponseDTO group;
    private List<GroupMemberResponseDTO> groupMembers;
}