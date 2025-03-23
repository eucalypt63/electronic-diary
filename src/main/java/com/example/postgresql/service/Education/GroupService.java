package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.repository.Education.Group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }

}
