package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.NewsRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.NewsResponseDTO;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.model.Education.News.NewsComment;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Education.NewsService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsControl {

    @Autowired
    private NewsService newsService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;
    @Autowired
    private UserService userService;
    @Autowired
    private DTOService dtoService;

    //Найти новость по id
    @GetMapping("/findNewsById")
    @ResponseBody
    public ResponseEntity<NewsResponseDTO> findNewsById(Long id) {
        NewsResponseDTO news = dtoService.NewsToDto(newsService.findNewsById(id));

        return ResponseEntity.ok(news);
    }

    //Найти все новости по id школы
    @GetMapping("/findNewsByEducationId")
    @ResponseBody
    public ResponseEntity<List<NewsResponseDTO>> findNewsByEducationId(Long id) {
        List<News> newsList = newsService.findNewsByEducationalInstitutionId(id);
        List<NewsResponseDTO> newsResponseDTOS = new ArrayList<>();

        newsList.forEach(news -> {
            newsResponseDTOS.add(dtoService.NewsToDto(news));
        });
        return ResponseEntity.ok(newsResponseDTOS);
    }

    //Добавить новость
    @GetMapping("/addNews")
    @ResponseBody
    public ResponseEntity<String> addNews(NewsRequestDTO newsRequestDTO) {
        News news = new News();
        news.setId(newsRequestDTO.getId());
        news.setEducationalInstitution(educationalInstitutionService.findEducationalInstitutionById(newsRequestDTO.getEducationalInstitutionId()));
        news.setOwnerUser(userService.findUserById(newsRequestDTO.getId()));
        news.setTitle(newsRequestDTO.getTitle());
        news.setContent(newsRequestDTO.getContent());
        news.setDateTime(newsRequestDTO.getDateTime());

        newsService.saveNews(news);
        return ResponseEntity.ok("{\"message\": \"Новость успешно добавлена\"}");
    }

    //Удалить новость по id
    @DeleteMapping("/deleteNewsById")
    @ResponseBody
    public ResponseEntity<Void> deleteNewsById(Long id) {
        newsService.deleteNewsById(id);
        return ResponseEntity.ok().build();
    }

    //---

    //Удалить комментарий по id
    @DeleteMapping("/deleteNewsCommentById")
    @ResponseBody
    public ResponseEntity<Void> deleteNewsCommentById(Long id) {
        newsService.deleteNewsCommentById(id);
        return ResponseEntity.ok().build();
    }
}
