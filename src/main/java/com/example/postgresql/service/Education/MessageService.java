package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Message;
import com.example.postgresql.repository.Education.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public Message findMessageById(Long id){
        return messageRepository.findById(id).orElse(null);
    }

    public List<Message> findAllMessages(){
        return messageRepository.findAll();
    }

    public void deleteMessageById(Long id){
        messageRepository.deleteById(id);
    }

}
