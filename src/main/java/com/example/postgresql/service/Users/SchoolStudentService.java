package com.example.postgresql.service.Users;

import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.repository.Users.Student.SchoolStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolStudentService {

    @Autowired
    private SchoolStudentRepository schoolStudentRepository;

    public List<SchoolStudent> findSchoolStudentByEducationalInstitutionId(Long id) {
        return schoolStudentRepository.findSchoolStudentByEducationalInstitutionId(id);
    }

    public SchoolStudent findSchoolStudentById(Long id) {
        return schoolStudentRepository.findById(id).orElse(null);
    }
    public SchoolStudent findSchoolStudentByUserId(Long id) {
        return schoolStudentRepository.findSchoolStudentByUserId(id);
    }
    public void saveSchoolStudent(SchoolStudent schoolStudent) {
        schoolStudentRepository.save(schoolStudent);
    }
    public void deleteSchoolStudentById(Long id) {
        schoolStudentRepository.deleteById(id);
    }
    public List<SchoolStudent> findAllSchoolStudentByClassId(Long id) {
        return schoolStudentRepository.findByClassRoomId(id);
    }

}
