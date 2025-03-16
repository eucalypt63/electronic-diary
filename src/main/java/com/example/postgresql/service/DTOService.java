package com.example.postgresql.service;

import com.example.postgresql.DTO.ResponseDTO.*;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Users.Education.Group;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.Teacher;
import org.springframework.stereotype.Service;

@Service
public class DTOService {
    public AdministratorResponseDTO AdministratorToDto(Administrator administrator)
    {
        AdministratorResponseDTO administratorResponseDTO = new AdministratorResponseDTO();
        administratorResponseDTO.setId(administrator.getId());
        administratorResponseDTO.setEducationalInstitution(administrator.getEducationalInstitution());
        administratorResponseDTO.setFirstName(administrator.getFirstName());
        administratorResponseDTO.setLastName(administrator.getLastName());
        administratorResponseDTO.setPatronymic(administrator.getPatronymic());
        administratorResponseDTO.setPathImage(administrator.getPathImage());
        administratorResponseDTO.setEmail(administrator.getEmail());
        administratorResponseDTO.setPhoneNumber(administrator.getPhoneNumber());

        return administratorResponseDTO;
    }

    public TeacherResponseDTO TeacherToDto(Teacher teacher)
    {
        TeacherResponseDTO teacherResponseDTO = new TeacherResponseDTO();
        teacherResponseDTO.setId(teacher.getId());
        teacherResponseDTO.setEducationalInstitution(teacher.getEducationalInstitution());
        teacherResponseDTO.setFirstName(teacher.getFirstName());
        teacherResponseDTO.setLastName(teacher.getLastName());
        teacherResponseDTO.setPatronymic(teacher.getPatronymic());
        teacherResponseDTO.setPathImage(teacher.getPathImage());
        teacherResponseDTO.setEmail(teacher.getEmail());
        teacherResponseDTO.setPhoneNumber(teacher.getPhoneNumber());

        return teacherResponseDTO;
    }

    public ParentResponseDTO ParentToDto(Parent parent)
    {
        ParentResponseDTO parentResponseDTO = new ParentResponseDTO();
        parentResponseDTO.setId(parent.getId());
        parentResponseDTO.setEducationalInstitution(parent.getEducationalInstitution());
        parentResponseDTO.setFirstName(parent.getFirstName());
        parentResponseDTO.setLastName(parent.getLastName());
        parentResponseDTO.setPatronymic(parent.getPatronymic());
        parentResponseDTO.setPathImage(parent.getPathImage());
        parentResponseDTO.setEmail(parent.getEmail());
        parentResponseDTO.setPhoneNumber(parent.getPhoneNumber());

        return parentResponseDTO;
    }

    public SchoolStudentResponseDTO SchoolStudentToDto(SchoolStudent schoolStudent)
    {
        SchoolStudentResponseDTO schoolStudentResponseDTO = new SchoolStudentResponseDTO();
        schoolStudentResponseDTO.setId(schoolStudent.getId());
        schoolStudentResponseDTO.setEducationalInstitution(schoolStudent.getEducationalInstitution());
        schoolStudentResponseDTO.setClassRoom(schoolStudent.getClassRoom());
        schoolStudentResponseDTO.setFirstName(schoolStudent.getFirstName());
        schoolStudentResponseDTO.setLastName(schoolStudent.getLastName());
        schoolStudentResponseDTO.setPatronymic(schoolStudent.getPatronymic());
        schoolStudentResponseDTO.setPathImage(schoolStudent.getPathImage());
        schoolStudentResponseDTO.setEmail(schoolStudent.getEmail());
        schoolStudentResponseDTO.setPhoneNumber(schoolStudent.getPhoneNumber());

        return schoolStudentResponseDTO;
    }

    public ClassResponseDTO ClassToDto(Class cl)
    {
        ClassResponseDTO classResponseDTO = new ClassResponseDTO();
        classResponseDTO.setId(cl.getId());
        classResponseDTO.setName(cl.getName());
        classResponseDTO.setTeacher(TeacherToDto(cl.getTeacher()));

        return classResponseDTO;
    }

    public StudentParentResponseDTO StudentParentToDto(StudentParent studentParent)
    {
        StudentParentResponseDTO studentParentResponseDTO = new StudentParentResponseDTO();
        studentParentResponseDTO.setId(studentParent.getId());
        studentParentResponseDTO.setSchoolStudent(SchoolStudentToDto(studentParent.getSchoolStudent()));
        studentParentResponseDTO.setParent(ParentToDto(studentParent.getParent()));
        studentParentResponseDTO.setParentType(studentParent.getParentType());

        return studentParentResponseDTO;
    }

    public TeacherAssignmentResponseDTO TeacherAssignmentToDto(TeacherAssignment teacherAssignment)
    {
        TeacherAssignmentResponseDTO teacherAssignmentResponseDTO = new TeacherAssignmentResponseDTO();
        teacherAssignmentResponseDTO.setId(teacherAssignment.getId());
        teacherAssignmentResponseDTO.setTeacher(TeacherToDto(teacherAssignment.getTeacher()));
        teacherAssignmentResponseDTO.setSchoolSubject(teacherAssignment.getSchoolSubject());
        teacherAssignmentResponseDTO.setClassRoom(ClassToDto(teacherAssignment.getClassRoom()));

        return teacherAssignmentResponseDTO;
    }

    public GroupResponseDTO GroupToDto(Group group)
    {
        GroupResponseDTO groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setId(group.getId());
        groupResponseDTO.setTeacherAssignment(TeacherAssignmentToDto(group.getTeacherAssignment()));
        groupResponseDTO.setSchoolStudent(SchoolStudentToDto(group.getSchoolStudent()));

        return groupResponseDTO;
    }
}
