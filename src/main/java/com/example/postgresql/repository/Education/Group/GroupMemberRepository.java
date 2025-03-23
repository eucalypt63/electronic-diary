package com.example.postgresql.repository.Education.Group;

import com.example.postgresql.model.Education.Group.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}