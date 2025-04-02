package com.example.postgresql.repository.Education.Group;

import com.example.postgresql.model.Education.Group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findGroupByClassRoomId(Long id);
    Group findGroupByClassRoomIdAndGroupName(Long classRoomId, String groupName);
}