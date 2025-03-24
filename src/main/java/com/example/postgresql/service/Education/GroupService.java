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

    //___

    public List<GroupMember> getAllGroupMember() {
        return groupMemberRepository.findAll();
    }

}
