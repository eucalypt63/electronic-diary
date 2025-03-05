package com.example.postgresql.service.Users;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.ParentType;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.repository.ClassesRepository;
import com.example.postgresql.repository.Users.Student.ParentRepository;
import com.example.postgresql.repository.Users.Student.ParentTypeRepository;
import com.example.postgresql.repository.Users.Student.StudentParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentTypeRepository parentTypeRepository;

    @Autowired
    private StudentParentRepository studentParentRepository;


    public Parent findParentById(Long id) { return  parentRepository.findById(id).orElse(null); }
    public void saveParent(Parent parent) {
        parentRepository.save(parent);
    }
    public void deleteParentById(Long id) {
        parentRepository.deleteById(id);
    }

    public List<ParentType> getAllParentTypes() { return parentTypeRepository.findAll(); }

    public List<StudentParent> getAllStudentParent() { return studentParentRepository.findAll(); }
    public void saveStudentParent(StudentParent studentParent) {
        studentParentRepository.save(studentParent);
    }

    public ParentType findParentTypeById(Long id) { return  parentTypeRepository.findById(id).orElse(null); }
}
