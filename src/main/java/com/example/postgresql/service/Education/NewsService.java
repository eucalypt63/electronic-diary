package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.repository.Education.News.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public News findNewsById(Long id){
        return newsRepository.findById(id).orElse(null);
    }

    public List<News> findNewsByEducationalInstitutionId(Long id){
        return newsRepository.findNewsByEducationalInstitutionId(id);
    }

    @Transactional
    public void saveNews(News news) {
        newsRepository.save(news);
    }

    @Transactional
    public void deleteNewsById(Long id){
        newsRepository.deleteById(id);
    }

}
