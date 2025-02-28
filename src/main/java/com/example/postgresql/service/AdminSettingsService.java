package com.example.postgresql.service;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.*;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.model.Users.User.UserType;
import com.example.postgresql.repository.ClassesRepository;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.Education.*;
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
public class AdminSettingsService {

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;
    @Autowired
    private EducationalInstitutionTypeRepository educationalInstitutionTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private SchoolStudentRepository schoolStudentRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassesRepository classesRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private StudentParentRepository studentParentRepository;


    public List<EducationalInstitution> getAllEducationalInstitution() {
        return educationalInstitutionRepository.findAll();
    }
    public EducationalInstitution getEducationalInstitutionById(Long id) {
        return educationalInstitutionRepository.findById(id).orElse(null);
    }

    public EducationalInstitutionType findEducationalInstitutionTypeById(Long id) {
        return educationalInstitutionTypeRepository.findById(id).orElse(null);
    }

    public void saveEducationalInstitutional(EducationalInstitution institution) {
        educationalInstitutionRepository.save(institution);
    }

    public void deleteEducationalInstitutionById(Long id) {
        educationalInstitutionRepository.deleteById(id);
    }

    public Settlement findSettlementById(Long id) {
        return settlementRepository.findById(id).orElse(null);
    }

    public List<Region> getAllRegion() {
        return regionRepository.findAll();
    }
    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }
    public List<Settlement> getAllSettlement() {
        return settlementRepository.findAll();
    }

    public List<SchoolStudent> getAllSchoolStudent() {
        return schoolStudentRepository.findAll();
    }

    public void saveSchoolStudent(SchoolStudent schoolStudent) {
        schoolStudentRepository.save(schoolStudent);
    }
    public SchoolStudent findSchoolStudentById(Long id) {
        return schoolStudentRepository.findById(id).orElse(null);
    }

    public List<StudentParent> getAllStudentParent() { return studentParentRepository.findAll(); }

    public List<Administrator> getAllAdministrator() {
        return administratorRepository.findAll();
    }

    public void saveAdministrator(Administrator administrator) {
        administratorRepository.save(administrator);
    }

    public void deleteAdministratorById(Long id) {
        administratorRepository.deleteById(id);
    }

    public UserType findUserTypeById(Long id) {
        return userTypeRepository.findById(id).orElse(null);
    }

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

    public List<Class> getAllClasses() {
        return classesRepository.findAll();
    }
    public void saveClass(Class cl) {
        classesRepository.save(cl);
    }

    public Class findClassById(Long id) {
        return classesRepository.findById(id).orElse(null);
    }
    public void deleteClassById(Long id) {
        classesRepository.deleteById(id);
    }

    public void saveParent(Parent parent) {
        parentRepository.save(parent);
    }
    public void deleteParentById(Long id) {
        parentRepository.deleteById(id);
    }

    public byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }
}
