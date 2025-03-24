package com.example.postgresql.controller.Education;

import com.example.postgresql.service.Education.QuarterScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuarterControl {
    @Autowired
    private QuarterScoreService quarterScoreService;

}
