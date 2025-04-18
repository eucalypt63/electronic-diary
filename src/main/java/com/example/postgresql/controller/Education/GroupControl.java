package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.GroupRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupInfoResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupMemberResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
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
        
        return ResponseEntity.ok("{\"message\": \"Группа успешно добавлена\"}");
    }

    //Пока не используется
    @DeleteMapping("/deleteGroupById")
    @ResponseBody
    public ResponseEntity<Void> deleteGroupById(@RequestParam Long id) {
        groupService.deleteGroupById(id);
        return ResponseEntity.ok().build();
    }

    //Пока не используется
    @PostMapping("/changeGroup")
    @ResponseBody
    public ResponseEntity<String> changeGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = groupService.findGroupById(groupRequestDTO.getId());
        group.setGroupName(groupRequestDTO.getGroupName());
        groupService.saveGroup(group);
        return ResponseEntity.ok().build();
    }


    //Ученики группы
    @GetMapping("/findGroupMembersByClassId")
    @ResponseBody
    public ResponseEntity<List<GroupInfoResponseDTO>> findGroupMembersByClassId (Long id) {
        List<Group> groups = groupService.findGroupByClassId(id);

        List<GroupInfoResponseDTO> groupInfoResponseDTOS = new ArrayList<>();
        groups.forEach(group -> {
            GroupResponseDTO groupResponseDTO = dtoService.GroupToDto(group);

            List<GroupMember> groupMembers = groupService.findGroupMemberByGroupId(groupResponseDTO.getId());
            List<GroupMemberResponseDTO> groupMemberResponseDTOS = new ArrayList<>();
            groupMembers.forEach(groupMember -> {
                groupMemberResponseDTOS.add(dtoService.GroupMemberToDto(groupMember));
            });

            GroupInfoResponseDTO groupInfoResponseDTO = new GroupInfoResponseDTO();
            groupInfoResponseDTO.setGroup(groupResponseDTO);
            groupInfoResponseDTO.setGroupMembers(groupMemberResponseDTOS);
            groupInfoResponseDTOS.add(groupInfoResponseDTO);
        });

        return ResponseEntity.ok(groupInfoResponseDTOS);
    }

    @PostMapping("/addGroupMember")
    @ResponseBody
    public ResponseEntity<String> addGroupMember(@RequestParam Long studentId, @RequestParam Long groupId){
        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(groupService.findGroupById(groupId));
        groupMember.setSchoolStudent(schoolStudentService.findSchoolStudentById(studentId));
        groupService.saveGroupMember(groupMember);

        return ResponseEntity.ok("{\"message\": \"Ученик успешно добавлен в группу\"}");
    }

    @PostMapping("/deleteGroupMember")
    @ResponseBody
    public ResponseEntity<String> deleteGroupMember(@RequestParam Long studentId, @RequestParam Long groupId){
        System.out.println(studentId);
        System.out.println(groupId);
        GroupMember groupMember = groupService.findGroupMemberByGroupIdAndSchoolStudentId(groupId, studentId);
        groupService.deleteGroupMember(groupMember);

        return ResponseEntity.ok("{\"message\": \"Ученик успешно удалён из группы\"}");
    }
}