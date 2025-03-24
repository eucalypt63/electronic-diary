package com.example.postgresql.controller.Education;

import com.example.postgresql.model.Education.Gradebook.*;
import com.example.postgresql.service.Education.GradebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GradebookControl {
    @Autowired
    private GradebookService gradebookService;

}
