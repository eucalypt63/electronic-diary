package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.MessageRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.MessageResponseDTO;
import com.example.postgresql.model.Education.Message;
import com.example.postgresql.model.Education.UserComment;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.MessageService;
import com.example.postgresql.service.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class MessageControl {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private DTOService dtoService;

    //Добавление сообщения
    @PostMapping("addMessage")
    @ResponseBody
    public ResponseEntity<String> addMessage(MessageRequestDTO messageRequestDTO){
        Message message = new Message();
        message.setId(messageRequestDTO.getId());
        message.setGetterUser(userService.findUserById(messageRequestDTO.getGetterUserId()));
        message.setSenderUser(userService.findUserById(messageRequestDTO.getSenderUserId()));
        message.setMessage(messageRequestDTO.getMessage());
        message.setDateTime(messageRequestDTO.getDateTime());
        messageService.saveMessage(message);

        return ResponseEntity.ok("{\"message\": \"Сообщение успешно отправлено\"}");
    }

    //Получение конкретного сообщения
    @GetMapping("/getMessageById")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(Long id) {
        return ResponseEntity.ok(messageService.findMessageById(id));
    }

    //Получить сообщения получателя
    @GetMapping("findMessageByGetterUserId")
    @ResponseBody
    public ResponseEntity<List<MessageResponseDTO>> findMessageByGetterUserId(Long id){
        List<Message> messages = messageService.findMessageByGetterUserId(id);
        List<MessageResponseDTO> messageResponseDTOS = new ArrayList<>();

        messages.forEach(message -> {
            messageResponseDTOS.add(dtoService.MessageToDto(message));
        });

        return ResponseEntity.ok(messageResponseDTOS);
    }

    //Получить сообщения от конкретного отправителя
    @GetMapping("findMessageBySenderUserIdAndGetterUserId")
    @ResponseBody
    public ResponseEntity<List<MessageResponseDTO>> findMessageBySenderUserIdAndGetterUserId(Long senderId, Long getterId){
        List<Message> messages = messageService.findMessageBySenderUserIdAndGetterUserId(senderId, getterId);
        List<MessageResponseDTO> messageResponseDTOS = new ArrayList<>();

        messages.forEach(message -> {
            messageResponseDTOS.add(dtoService.MessageToDto(message));
        });

        return ResponseEntity.ok(messageResponseDTOS);
    }

    //Получить последних сообщений от каждого отправителя
    @GetMapping("findLatestMessageByGetterUserId")
    @ResponseBody
    public ResponseEntity<List<MessageResponseDTO>> findLatestMessageByGetterUserId(Long id){
        List<Message> messages = messageService.findMessageByGetterUserId(id);
        List<MessageResponseDTO> messageResponseDTOS = new ArrayList<>();

        Map<User, Message> latestMessagesBySender = messages.stream()
                .collect(Collectors.toMap(
                        Message::getSenderUser,
                        Function.identity(),
                        (existing, replacement) ->
                                existing.getDateTime().isAfter(replacement.getDateTime()) ? existing : replacement
                ));

        latestMessagesBySender.values().forEach(message -> {
            messageResponseDTOS.add(dtoService.MessageToDto(message));
        });

        return ResponseEntity.ok(messageResponseDTOS);
    }

    //Удалить сообщение по id
    @DeleteMapping("/deleteMessageById")
    @ResponseBody
    public ResponseEntity<Void> deleteMessageById(Long id) {
        messageService.deleteMessageById(id);
        return ResponseEntity.ok().build();
    }

}
