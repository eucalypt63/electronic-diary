package com.example.postgresql.DTO.ResponseDTO.Schedule;

import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.TeacherResponseDTO;
import com.example.postgresql.model.SchoolSubject;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class LessonParamsResponseDTO {
    private List<GroupResponseDTO> groups;
    private List<TeacherResponseDTO> teachers;
    private List<SchoolSubject> schoolSubjects;
}