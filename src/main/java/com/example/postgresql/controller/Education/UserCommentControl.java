package com.example.postgresql.controller.Education;

import com.example.postgresql.model.Education.Message;
import com.example.postgresql.model.Education.UserComment;
import com.example.postgresql.service.Education.MessageService;
import com.example.postgresql.service.Education.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserCommentControl {

    @Autowired
    private UserCommentService userCommentService;

    @GetMapping("/findUserCommentById")
    @ResponseBody
    public ResponseEntity<UserComment> findUserCommentById(Long id) {
        return ResponseEntity.ok(userCommentService.findUserCommentById(id));
    }

    @GetMapping("/findAllUserComments")
    @ResponseBody
    public ResponseEntity<List<UserComment>> findAllUserComments() {
        return ResponseEntity.ok(userCommentService.findAllUserComments());
    }

    @DeleteMapping("/deleteUserCommentById")
    @ResponseBody
    public ResponseEntity<Void> deleteUserCommentById(Long id) {
        userCommentService.deleteUserCommentById(id);
        return ResponseEntity.ok().build();
    }

}
