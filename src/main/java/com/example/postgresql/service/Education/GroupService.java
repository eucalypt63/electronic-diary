package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.repository.Education.Group.GroupMemberRepository;
import com.example.postgresql.repository.Education.Group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public Group findGroupByClassRoomIdAndGroupName(Long classRoomId, String groupName) {
        return groupRepository.findGroupByClassRoomIdAndGroupName(classRoomId, groupName);
    }

    public Group findGroupById(Long id){
        return groupRepository.findById(id).orElse(null);
    }

    public List<Group> findGroupByClassId(Long id) {return groupRepository.findGroupByClassRoomId(id);}

    public void deleteGroupById(Long id){ groupRepository.deleteById(id);}

    public void saveGroup(Group group) { groupRepository.save(group);}

    //___

    public GroupMember findGroupMemberByGroupIdAndSchoolStudentId(Long groupId, Long studentId){ return groupMemberRepository.findGroupMemberByGroupIdAndSchoolStudentId(groupId, studentId);}
    public List<GroupMember> findGroupMemberBySchoolStudentId(Long studentId){ return groupMemberRepository.findGroupMemberBySchoolStudentId(studentId);}
    public void deleteGroupMember(GroupMember groupMember){groupMemberRepository.delete(groupMember);}
    public void saveGroupMember(GroupMember groupMember) { groupMemberRepository.save(groupMember);}

    public List<GroupMember> findGroupMemberByGroupId(Long id){ return groupMemberRepository.findGroupMemberByGroupId(id);}

}
