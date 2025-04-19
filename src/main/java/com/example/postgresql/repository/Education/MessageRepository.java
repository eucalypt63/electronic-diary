package com.example.postgresql.repository.Education;

import com.example.postgresql.model.Education.Message;
import com.example.postgresql.model.Education.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessageByGetterUserId(Long id);
    List<Message> findMessageBySenderUserIdAndGetterUserId(Long senderId, Long getterId);
}