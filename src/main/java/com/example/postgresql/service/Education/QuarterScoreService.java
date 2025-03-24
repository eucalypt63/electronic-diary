package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Message;
import com.example.postgresql.repository.Education.Gradebook.QuarterScoreRepository;
import com.example.postgresql.repository.Education.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuarterScoreService {

    @Autowired
    private QuarterScoreRepository quarterScoreRepository;


}
