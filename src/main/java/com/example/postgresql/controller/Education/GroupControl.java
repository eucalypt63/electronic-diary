package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.GroupRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.GroupResponseDTO;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Education.GroupService;
import com.example.postgresql.service.Users.SchoolStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupControl {
    @Autowired
    private GroupService groupService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private ClassService classService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("/findGroupsByClassId")
    @ResponseBody
    public ResponseEntity<List<GroupResponseDTO>> findGroupsByClassId (Long id){
        List<Group> groups = groupService.findGroupByClassId(id);

        if (groups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        List<GroupResponseDTO> groupResponseDTOS = new ArrayList<>();
        for (Group group : groups) {
            groupResponseDTOS.add(dtoService.GroupToDto(group));
        }

        return ResponseEntity.ok(groupResponseDTOS);
    }

    @PostMapping("/addGroup")
    @ResponseBody
    public ResponseEntity<String> addGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = new Group();
        group.setClassRoom(classService.findClassById(groupRequestDTO.getClassRoom()));
        group.setGroupName(groupRequestDTO.getGroupName());
        groupService.saveGroup(group);

        groupRequestDTO.getGroupMembersId().forEach(groupMemberId -> {
            GroupMember member = new GroupMember();
            member.setSchoolStudent(schoolStudentService.findSchoolStudentById(groupMemberId));
            member.setGroup(group);
            groupService.saveGroupMember(member);
        });
        
        return ResponseEntity.ok("{\"message\": \"Группа успешно добавлена\"}");
    }

    @DeleteMapping("/deleteGroupById")
    @ResponseBody
    public ResponseEntity<Void> deleteGroupById(Long id) {
        groupService.deleteGroupById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeGroup")
    @ResponseBody
    public ResponseEntity<String> changeGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = groupService.findGroupById(groupRequestDTO.getId());
        group.setGroupName(groupRequestDTO.getGroupName());
        groupService.saveGroup(group);
        return ResponseEntity.ok().build();
    }
}
