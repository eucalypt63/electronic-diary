package com.example.postgresql.repository.Education.Group;

import com.example.postgresql.model.Education.Group.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findGroupMemberByGroupId(Long id);
    GroupMember findGroupMemberByGroupIdAndSchoolStudentId(Long groupId, Long studentId);
}