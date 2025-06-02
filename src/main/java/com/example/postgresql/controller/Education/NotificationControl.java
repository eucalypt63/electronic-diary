package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.ResponseDTO.NotificationResponseDTO;
import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Education.Notification;
import com.example.postgresql.model.Users.User.User;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NotificationControl {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("/findNotificationByUserId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<NotificationResponseDTO>> findNotificationByUserId(@RequestParam Long id){
        List<Notification> notifications = notificationService.findNotificationByUserId(id);
        List<NotificationResponseDTO> notificationResponseDTOS = new ArrayList<>();
        notifications.forEach(notification -> {
            notificationResponseDTOS.add(dtoService.NotificationToDto(notification));
        });

        return ResponseEntity.ok(notificationResponseDTOS);
    }

    @DeleteMapping("deleteNotificationById")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<String> deleteNotificationById(@RequestParam Long id){
        notificationService.deleteNotificationById(id);
        return ResponseEntity.ok("{\"message\": \"Уведомление успешно удалено\"}");
    }
}
