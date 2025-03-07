package com.example.postgresql.service.Users;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.Group;
import com.example.postgresql.model.Users.Education.Region;
import com.example.postgresql.model.Users.Education.Settlement;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.repository.ClassesRepository;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.Education.GroupRepository;
import com.example.postgresql.repository.Users.Education.RegionRepository;
import com.example.postgresql.repository.Users.Education.SettlementRepository;
import com.example.postgresql.repository.Users.Student.ParentRepository;
import com.example.postgresql.repository.Users.Student.SchoolStudentRepository;
import com.example.postgresql.repository.Users.Student.StudentParentRepository;
import com.example.postgresql.repository.Users.TeacherRepository;
import com.example.postgresql.repository.Users.User.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class SchoolStudentService {

    @Autowired
    private SchoolStudentRepository schoolStudentRepository;


    public List<SchoolStudent> getAllSchoolStudent() {
        return schoolStudentRepository.findAll();
    }

    public List<SchoolStudent> findSchoolStudentByEducationalInstitutionId(Long id) {
        return schoolStudentRepository.findSchoolStudentByEducationalInstitutionId(id);
    }

    public void saveSchoolStudent(SchoolStudent schoolStudent) {
        schoolStudentRepository.save(schoolStudent);
    }
    public SchoolStudent findSchoolStudentById(Long id) {
        return schoolStudentRepository.findById(id).orElse(null);
    }

    public void deleteSchoolStudentById(Long id) {
        schoolStudentRepository.deleteById(id);
    }

    public List<SchoolStudent> getAllSchoolStudentByClassId(Long id) {
        return schoolStudentRepository.findByClassRoomId(id);
    }

}
