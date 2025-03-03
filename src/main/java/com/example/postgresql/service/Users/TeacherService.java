package com.example.postgresql.service.Users;

import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }
}
