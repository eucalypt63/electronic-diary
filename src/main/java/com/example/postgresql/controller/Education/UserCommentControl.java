package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.UserCommentRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.UserCommentResponseDTO;
import com.example.postgresql.model.Education.Message;
import com.example.postgresql.model.Education.UserComment;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.MessageService;
import com.example.postgresql.service.Education.UserCommentService;
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

@Controller
public class UserCommentControl {

    @Autowired
    private UserCommentService userCommentService;
    @Autowired
    private UserService userService;
    @Autowired
    private DTOService dtoService;

    //Добавление комментария пользователю
    @PostMapping("addUserComment")
    @ResponseBody
    public ResponseEntity<String> addUserComment(UserCommentRequestDTO userCommentRequestDTO){
        UserComment userComment = new UserComment();
        userComment.setId(userCommentRequestDTO.getId());
        userComment.setGetterUser(userService.findUserById(userCommentRequestDTO.getGetterUserId()));
        userComment.setSenderUser(userService.findUserById(userCommentRequestDTO.getSenderUserId()));
        userComment.setContent(userCommentRequestDTO.getContent());
        userComment.setDateTime(userCommentRequestDTO.getDateTime());
        userCommentService.saveUserComment(userComment);

        return ResponseEntity.ok("{\"message\": \"Комментарий пользователю успешно добавлен\"}");
    }

    //Получение конкретного комментария
    @GetMapping("/findUserCommentById")
    @ResponseBody
    public ResponseEntity<UserCommentResponseDTO> findUserCommentById(Long id) {
        return ResponseEntity.ok(dtoService.UserCommentToDto(userCommentService.findUserCommentById(id)));
    }

    //Получение всех комментариев текущего пользователя
    @GetMapping("findUserCommentByGetterUserId")
    @ResponseBody
    public ResponseEntity<List<UserCommentResponseDTO>> findUserCommentByGetterUserId(Long id){
        List<UserComment> userComments = userCommentService.findUserCommentByGetterUserId(id);
        List<UserCommentResponseDTO> userCommentResponseDTOS = new ArrayList<>();

        userComments.forEach(userComment -> {
            userCommentResponseDTOS.add(dtoService.UserCommentToDto(userComment));
        });

        return ResponseEntity.ok(userCommentResponseDTOS);
    }

    //Получение всех комментариев от отправителя
    @GetMapping("findUserCommentBySenderUserId")
    @ResponseBody
    public ResponseEntity<List<UserCommentResponseDTO>> findUserCommentBySenderUserId(Long senderId, Long getterId){
        List<UserComment> userComments = userCommentService.findUserCommentBySenderUserIdAndGetterUserId(senderId, getterId);
        List<UserCommentResponseDTO> userCommentResponseDTOS = new ArrayList<>();

        userComments.forEach(userComment -> {
            userCommentResponseDTOS.add(dtoService.UserCommentToDto(userComment));
        });

        return ResponseEntity.ok(userCommentResponseDTOS);
    }

    //Удаление комментария к пользователю по id
    @DeleteMapping("/deleteUserCommentById")
    @ResponseBody
    public ResponseEntity<Void> deleteUserCommentById(Long id) {
        userCommentService.deleteUserCommentById(id);
        return ResponseEntity.ok().build();
    }

}
