package com.example.postgresql.controller.Education;

import com.example.postgresql.model.Education.Message;
import com.example.postgresql.service.Education.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MessageControl {

    @Autowired
    private MessageService messageService;

    @GetMapping("/getMessageById")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(Long id) {
        return ResponseEntity.ok(messageService.findMessageById(id));
    }

    @GetMapping("/findAllMessage")
    @ResponseBody
    public ResponseEntity<List<Message>> findAllMessage() {
        return ResponseEntity.ok(messageService.findAllMessages());
    }

    @DeleteMapping("/deleteMessageById")
    @ResponseBody
    public ResponseEntity<Void> deleteMessageById(Long id) {
        messageService.deleteMessageById(id);
        return ResponseEntity.ok().build();
    }

}
