package com.example.postgresql.service;

import com.example.postgresql.model.School;
import com.example.postgresql.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    public School createSchool(String name) {
        School school = new School(name);
        return schoolRepository.save(school);
    }

    public List<School> getAllschools() {
        return schoolRepository.findAll();
    }
}
