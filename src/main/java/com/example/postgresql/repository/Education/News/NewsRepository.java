package com.example.postgresql.repository.Education.News;

import com.example.postgresql.model.Education.News.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}