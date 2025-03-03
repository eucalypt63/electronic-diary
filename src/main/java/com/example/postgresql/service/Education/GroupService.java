package com.example.postgresql.service.Education;

import com.example.postgresql.model.Users.Education.Group;
import com.example.postgresql.model.Users.Education.Region;
import com.example.postgresql.model.Users.Education.Settlement;
import com.example.postgresql.repository.Users.Education.GroupRepository;
import com.example.postgresql.repository.Users.Education.RegionRepository;
import com.example.postgresql.repository.Users.Education.SettlementRepository;
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
