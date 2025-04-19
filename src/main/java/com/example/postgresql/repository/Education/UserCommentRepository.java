package com.example.postgresql.repository.Education;

import com.example.postgresql.model.Education.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
    List<UserComment> findUserCommentByGetterUserId(Long id);
    List<UserComment> findUserCommentBySenderUserIdAndGetterUserId(Long senderId, Long getterId);
}