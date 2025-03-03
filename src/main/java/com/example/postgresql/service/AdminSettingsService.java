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
    private ParentRepository parentRepository;
    @Autowired
    private StudentParentRepository studentParentRepository;




}
