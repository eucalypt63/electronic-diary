package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.News.NewsRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsResponseDTO;
import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.Education.NewsService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<NewsResponseDTO> findNewsById(@RequestParam Long id) {
        NewsResponseDTO news = dtoService.NewsToDto(newsService.findNewsById(id));

        return ResponseEntity.ok(news);
    }

    //Найти все новости по id школы
    @GetMapping("/findNewsByEducationId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
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
    @RequiredRoles({"Main admin", "Local admin", "Administration"})
    @ResponseBody
    public ResponseEntity<String> addNews(@RequestBody NewsRequestDTO newsRequestDTO, HttpSession session) {
        News news = new News();
        news.setEducationalInstitution(educationalInstitutionService.findEducationalInstitutionById(newsRequestDTO.getEducationalInstitutionId()));
        news.setOwnerUser(userService.findUserById((Long) session.getAttribute("userId")));
        news.setTitle(newsRequestDTO.getTitle());
        news.setContent(newsRequestDTO.getContent());
        news.setDateTime(newsRequestDTO.getDateTime());

        newsService.saveNews(news);
        return ResponseEntity.ok("{\"message\": \"Новость успешно добавлена\"}");
    }

    //Обновить новость
    @PostMapping("/changeNews")
    @RequiredRoles({"Main admin", "Local admin", "Administration"})
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
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<Void> deleteNewsById(@RequestParam Long id) {
        newsService.deleteNewsById(id);
        return ResponseEntity.ok().build();
    }
}
