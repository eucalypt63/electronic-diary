package com.example.postgresql.service;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.Education.EducationalInstitutionType;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.repository.ClassesRepository;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.Education.EducationalInstitutionRepository;
import com.example.postgresql.repository.Users.Education.EducationalInstitutionTypeRepository;
import com.example.postgresql.repository.Users.Student.SchoolStudentRepository;
import com.example.postgresql.repository.Users.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSettingsService {

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;
    @Autowired
    private EducationalInstitutionTypeRepository educationalInstitutionTypeRepository;
    @Autowired
    private SchoolStudentRepository schoolStudentRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassesRepository classesRepository;


    public List<EducationalInstitution> getAllEducationalInstitution() {
        return educationalInstitutionRepository.findAll();
    }
    public EducationalInstitution getEducationalInstitutionById(Long id) {
        return educationalInstitutionRepository.findEducationalInstitutionById(id);
    }

    public EducationalInstitutionType findEducationalInstitutionTypeById(Long id) {
        return educationalInstitutionTypeRepository.findById(id).orElse(null);
    }

    public void saveEducationalInstitutionsave(EducationalInstitution institution) {
        educationalInstitutionRepository.save(institution);
    }

    public void delateEducationalInstitutionById(Long id) {
        educationalInstitutionRepository.deleteById(id);
    }

    public List<SchoolStudent> getAllSchoolStudent() {
        return schoolStudentRepository.findAll();
    }


    public List<Administrator> getAllAdministrator() {
        return administratorRepository.findAll();
    }


    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }


    public List<Class> getAllClasses() {
        return classesRepository.findAll();
    }
}
