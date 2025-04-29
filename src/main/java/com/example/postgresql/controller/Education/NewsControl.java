package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.News.NewsCommentRequestDTO;
import com.example.postgresql.DTO.RequestDTO.News.NewsRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsCommentResponseDTO;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.model.Education.News.NewsComment;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Education.NewsService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<NewsResponseDTO> findNewsById(@RequestParam Long id) {
        NewsResponseDTO news = dtoService.NewsToDto(newsService.findNewsById(id));

        return ResponseEntity.ok(news);
    }

    //Найти все новости по id школы
    @GetMapping("/findNewsByEducationId")
    @ResponseBody
    public ResponseEntity<List<NewsResponseDTO>> findNewsByEducationId(@RequestParam Long id) {
        List<News> newsList = newsService.findNewsByEducationalInstitutionId(id);
        List<NewsResponseDTO> newsResponseDTOS = new ArrayList<>();

        newsList.forEach(news -> {
            newsResponseDTOS.add(dtoService.NewsToDto(news));
        });
        return ResponseEntity.ok(newsResponseDTOS);
    }

    //Добавить новость
    @PostMapping("/addNews")
    @ResponseBody
    public ResponseEntity<String> addNews(@RequestBody NewsRequestDTO newsRequestDTO) {
        News news = new News();
        news.setEducationalInstitution(educationalInstitutionService.findEducationalInstitutionById(newsRequestDTO.getEducationalInstitutionId()));
        news.setOwnerUser(userService.findUserById(newsRequestDTO.getOwnerUserId()));
        news.setTitle(newsRequestDTO.getTitle());
        news.setContent(newsRequestDTO.getContent());
        news.setDateTime(newsRequestDTO.getDateTime());

        newsService.saveNews(news);
        return ResponseEntity.ok("{\"message\": \"Новость успешно добавлена\"}");
    }

    //Обновить новость
    @PostMapping("/changeNews")
    @ResponseBody
    public ResponseEntity<String> changeNews(@RequestBody NewsRequestDTO newsRequestDTO) {
        News news = newsService.findNewsById(newsRequestDTO.getId());
        news.setTitle(newsRequestDTO.getTitle());
        news.setContent(newsRequestDTO.getContent());

        newsService.saveNews(news);
        return ResponseEntity.ok("{\"message\": \"Новость успешно обновлена\"}");
    }

    //Удалить новость по id
    @DeleteMapping("/deleteNewsById")
    @ResponseBody
    public ResponseEntity<Void> deleteNewsById(@RequestParam Long id) {
        newsService.deleteNewsById(id);
        return ResponseEntity.ok().build();
    }

    //---

    //Найти комментарий к новости по id
    @GetMapping("findNewsCommentById")
    @ResponseBody
    public ResponseEntity<NewsCommentResponseDTO> findNewsCommentById(@RequestParam Long id){
        NewsCommentResponseDTO newsCommentResponseDTO = dtoService.NewsCommentToDto(newsService.findNewsCommentById(id));
        return ResponseEntity.ok(newsCommentResponseDTO);
    }

    //Найти все комментарии к новости по id новости
    @GetMapping("findNewsCommentByNewsId")
    @ResponseBody
    public ResponseEntity<List<NewsCommentResponseDTO>> findNewsCommentByNewsId(@RequestParam Long id){
        List<NewsComment> newsComments = newsService.findNewsCommentByNewsId(id);
        List<NewsCommentResponseDTO> newsCommentResponseDTOS = new ArrayList<>();

        newsComments.forEach(newsComment -> {
            NewsCommentResponseDTO newsCommentResponseDTO = dtoService.NewsCommentToDto(newsService.findNewsCommentById(id));
            newsCommentResponseDTOS.add(newsCommentResponseDTO);
        });
        return ResponseEntity.ok(newsCommentResponseDTOS);
    }

    //Добавить комментарий к новости
    @PostMapping("addNewsComment")
    @ResponseBody
    public ResponseEntity<String> addNewsComment(@RequestBody NewsCommentRequestDTO newsCommentRequestDTO){
        NewsComment newsComment = new NewsComment();
        newsComment.setNews(newsService.findNewsById(newsCommentRequestDTO.getNewsId()));
        newsComment.setUser(userService.findUserById(newsCommentRequestDTO.getUserId()));
        newsComment.setContent(newsCommentRequestDTO.getContent());
        newsComment.setDateTime(newsCommentRequestDTO.getDateTime());
        newsService.saveNewsComment(newsComment);

        return ResponseEntity.ok("{\"message\": \"Комментарий к новости успешно добавлена\"}");
    }

    //Обновить комментарий к новости
    @PostMapping("changeNewsComment")
    @ResponseBody
    public ResponseEntity<String> changeNewsComment(@RequestBody NewsCommentRequestDTO newsCommentRequestDTO){
        NewsComment newsComment = newsService.findNewsCommentById(newsCommentRequestDTO.getId());
        newsComment.setContent(newsCommentRequestDTO.getContent());
        newsService.saveNewsComment(newsComment);

        return ResponseEntity.ok("{\"message\": \"Комментарий к новости успешно обновлён\"}");
    }

    //Удалить комментарий по id
    @DeleteMapping("/deleteNewsCommentById")
    @ResponseBody
    public ResponseEntity<Void> deleteNewsCommentById(@RequestParam Long id) {
        newsService.deleteNewsCommentById(id);
        return ResponseEntity.ok().build();
    }
}
