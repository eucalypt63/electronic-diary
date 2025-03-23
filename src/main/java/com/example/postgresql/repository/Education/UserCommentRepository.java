package com.example.postgresql.repository.Education;

import com.example.postgresql.model.Education.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
}