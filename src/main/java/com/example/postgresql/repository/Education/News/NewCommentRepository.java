package com.example.postgresql.repository.Education.News;

import com.example.postgresql.model.Education.News.NewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewCommentRepository extends JpaRepository<NewComment, Long> {
}