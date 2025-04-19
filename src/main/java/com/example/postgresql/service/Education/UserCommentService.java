package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Message;
import com.example.postgresql.model.Education.UserComment;
import com.example.postgresql.repository.Education.MessageRepository;
import com.example.postgresql.repository.Education.UserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommentService {

    @Autowired
    private UserCommentRepository userCommentRepository;

    public UserComment findUserCommentById(Long id){
        return userCommentRepository.findById(id).orElse(null);
    }

    public List<UserComment> findUserCommentByGetterUserId(Long id){
        return userCommentRepository.findUserCommentByGetterUserId(id);
    }

    public List<UserComment> findUserCommentBySenderUserIdAndGetterUserId(Long senderId, Long getterId){
        return userCommentRepository.findUserCommentBySenderUserIdAndGetterUserId(senderId, getterId);
    }

    public void deleteUserCommentById(Long id){
        userCommentRepository.deleteById(id);
    }

    public void saveUserComment(UserComment userComment){userCommentRepository.save(userComment);}

}
