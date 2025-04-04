package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GroupRequestDTO {
    private Long id;
    private Long classRoom;
    private String groupName;
    private List<Long> groupMembersId;
}