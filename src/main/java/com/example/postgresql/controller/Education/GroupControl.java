package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.GroupRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupInfoResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupMemberResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Education.Notification;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Education.GroupService;
import com.example.postgresql.service.Education.NotificationService;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private NotificationService notificationService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("/findGroupsByClassId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<GroupResponseDTO>> findGroupsByClassId (@RequestParam Long id){
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
    @RequiredRoles({"Main admin", "Local admin"})
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
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<Void> deleteGroupById(@RequestParam Long id) {
        groupService.deleteGroupById(id);
        return ResponseEntity.ok().build();
    }

    //Пока не используется
    @PostMapping("/changeGroup")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> changeGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = groupService.findGroupById(groupRequestDTO.getId());
        group.setGroupName(groupRequestDTO.getGroupName());
        groupService.saveGroup(group);
        return ResponseEntity.ok().build();
    }

    //Ученики группы
    @GetMapping("/findGroupMembersByClassId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<GroupInfoResponseDTO>> findGroupMembersByClassId (@RequestParam Long id) {
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

    @GetMapping("/findGroupMemberByGroupId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<GroupInfoResponseDTO> findGroupMemberByGroupId (@RequestParam Long id) {
        Group group = groupService.findGroupById(id);
        GroupResponseDTO groupResponseDTO = dtoService.GroupToDto(group);

        List<GroupMember> groupMembers = groupService.findGroupMemberByGroupId(groupResponseDTO.getId());
        List<GroupMemberResponseDTO> groupMemberResponseDTOS = new ArrayList<>();
        groupMembers.forEach(groupMember -> {
            groupMemberResponseDTOS.add(dtoService.GroupMemberToDto(groupMember));
        });

        GroupInfoResponseDTO groupInfoResponseDTO = new GroupInfoResponseDTO();
        groupInfoResponseDTO.setGroup(groupResponseDTO);
        groupInfoResponseDTO.setGroupMembers(groupMemberResponseDTOS);

        return ResponseEntity.ok(groupInfoResponseDTO);
    }

    @GetMapping("/findGroupMemberByTeacherAssignmentId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<GroupInfoResponseDTO> findGroupMemberByTeacherAssignmentId (@RequestParam Long id) {
        TeacherAssignment teacherAssignment = teacherService.findTeacherAssignmentById(id);
        Group group = groupService.findGroupById(teacherAssignment.getGroup().getId());
        GroupResponseDTO groupResponseDTO = dtoService.GroupToDto(group);

        List<GroupMember> groupMembers = groupService.findGroupMemberByGroupId(groupResponseDTO.getId());
        List<GroupMemberResponseDTO> groupMemberResponseDTOS = new ArrayList<>();
        groupMembers.forEach(groupMember -> {
            groupMemberResponseDTOS.add(dtoService.GroupMemberToDto(groupMember));
        });

        GroupInfoResponseDTO groupInfoResponseDTO = new GroupInfoResponseDTO();
        groupInfoResponseDTO.setGroup(groupResponseDTO);
        groupInfoResponseDTO.setGroupMembers(groupMemberResponseDTOS);

        return ResponseEntity.ok(groupInfoResponseDTO);
    }

    @PostMapping("/addGroupMember")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> addGroupMember(@RequestParam Long studentId, @RequestParam Long groupId){
        Group group = groupService.findGroupById(groupId);
        SchoolStudent schoolStudent = schoolStudentService.findSchoolStudentById(studentId);

        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(group);
        groupMember.setSchoolStudent(schoolStudent);
        groupService.saveGroupMember(groupMember);

        return ResponseEntity.ok("{\"message\": \"Ученик успешно добавлен в группу\"}");
    }

    @PostMapping("/deleteGroupMember")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> deleteGroupMember(@RequestParam Long studentId, @RequestParam Long groupId){
        GroupMember groupMember = groupService.findGroupMemberByGroupIdAndSchoolStudentId(groupId, studentId);
        groupService.deleteGroupMember(groupMember);

        return ResponseEntity.ok("{\"message\": \"Ученик успешно удалён из группы\"}");
    }
}