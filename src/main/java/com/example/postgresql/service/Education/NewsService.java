package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.News.NewsComment;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.repository.Education.News.NewsCommentRepository;
import com.example.postgresql.repository.Education.News.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsCommentRepository newsCommentRepository;

    public News findNewsById(Long id){
        return newsRepository.findById(id).orElse(null);
    }

    public List<News> findNewsByEducationalInstitutionId(Long id){
        return newsRepository.findNewsByEducationalInstitutionId(id);
    }

    public void saveNews(News news) {
        newsRepository.save(news);
    }

    public void deleteNewsById(Long id){
        newsRepository.deleteById(id);
    }

    //добавление

    //----------

    public NewsComment findNewsCommentById(Long id){
        return newsCommentRepository.findById(id).orElse(null);
    }

    public List<NewsComment> findNewsCommentByNewsId(Long id){
        return newsCommentRepository.findNewsCommentByNewsId(id);
    }

    public void saveNewsComment(NewsComment newsComment) {
        newsCommentRepository.save(newsComment);
    }

    public void deleteNewsCommentById(Long id){
        newsCommentRepository.deleteById(id);
    }

    //добавление
}
