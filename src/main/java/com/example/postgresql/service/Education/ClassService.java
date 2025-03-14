package com.example.postgresql.service.Education;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.repository.ClassesRepository;
import com.example.postgresql.repository.Users.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassesRepository classesRepository;

    public Class findClassById(Long id) {
        return classesRepository.findById(id).orElse(null);
    }
    public Class findClassByTeacherId(Long id) {
        return classesRepository.findClassByTeacherId(id);
    }
    public List<Class> findAllByTeacherEducationalInstitutionId(Long id) {
        return classesRepository.findAllByTeacherEducationalInstitutionId(id);
    }

    public void saveClass(Class cl) {
        classesRepository.save(cl);
    }
    public void deleteClassById(Long id) {
        classesRepository.deleteById(id);
    }
}
