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

    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }

    public Group findGroupByClassRoomIdAndGroupName(Long classRoomId, String groupName) {
        return groupRepository.findGroupByClassRoomIdAndGroupName(classRoomId, groupName);
    }

    public List<Group> findGroupByClassId(Long id) {return groupRepository.findGroupByClassRoomId(id);}

    public void deleteGroupById(Long id){ groupRepository.deleteById(id);}

    public void saveGroup(Group group) { groupRepository.save(group);}

    //___

    public List<GroupMember> getAllGroupMember() {
        return groupMemberRepository.findAll();
    }

    public void saveGroupMember(GroupMember groupMember) { groupMemberRepository.save(groupMember);}

}
