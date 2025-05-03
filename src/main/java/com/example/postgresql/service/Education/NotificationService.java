package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Notification;
import com.example.postgresql.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(Notification notification){notificationRepository.save(notification);}
    public void deleteNotificationById(Long id){notificationRepository.deleteById(id);}
    public List<Notification>findNotificationByUserId(Long id){return notificationRepository.findNotificationByUserId(id);}

}
