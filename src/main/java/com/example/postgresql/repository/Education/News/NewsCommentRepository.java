package com.example.postgresql.repository.Education.News;

import com.example.postgresql.model.Education.News.NewsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsCommentRepository extends JpaRepository<NewsComment, Long> {
    List<NewsComment> findAllByNewsId(Long id);
}