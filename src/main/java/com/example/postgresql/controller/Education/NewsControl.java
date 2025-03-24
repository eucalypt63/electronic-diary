package com.example.postgresql.controller.Education;

import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.model.Education.News.NewsComment;
import com.example.postgresql.service.Education.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NewsControl {

    @Autowired
    private NewsService newsService;

    @GetMapping("/findNewsById")
    @ResponseBody
    public ResponseEntity<News> findNewsById(Long id) {
        return ResponseEntity.ok(newsService.findNewsById(id));
    }

    @GetMapping("/findAllNews")
    @ResponseBody
    public ResponseEntity<List<News>> findAllNews() {
        return ResponseEntity.ok(newsService.findAllNews());
    }

    @DeleteMapping("/deleteNewsById")
    @ResponseBody
    public ResponseEntity<Void> deleteNewsById(Long id) {
        newsService.deleteNewsById(id);
        return ResponseEntity.ok().build();
    }

    //---

    @GetMapping("/findAllNewsComment")
    @ResponseBody
    public ResponseEntity<List<NewsComment>> findAllNewsComment(Long id) {
        return ResponseEntity.ok(newsService.findAllNewsCommentByNewsId(id));
    }

    @DeleteMapping("/deleteNewsCommentById")
    @ResponseBody
    public ResponseEntity<Void> deleteNewsCommentById(Long id) {
        newsService.deleteNewsCommentById(id);
        return ResponseEntity.ok().build();
    }
}
