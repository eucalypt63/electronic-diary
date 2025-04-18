package com.example.postgresql.service;

import com.example.postgresql.DTO.ResponseDTO.*;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupMemberResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.AdministratorResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.ParentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.SchoolStudentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.TeacherResponseDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Administrator;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.repository.Users.AdministratorRepository;
import com.example.postgresql.repository.Users.Student.ParentRepository;
import com.example.postgresql.repository.Users.Student.SchoolStudentRepository;
import com.example.postgresql.repository.Users.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DTOService {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SchoolStudentRepository schoolStudentRepository;

    public AdministratorResponseDTO AdministratorToDto(Administrator administrator)
    {
        AdministratorResponseDTO administratorResponseDTO = new AdministratorResponseDTO();
        administratorResponseDTO.setId(administrator.getId());
        administratorResponseDTO.setUserid(administrator.getUser().getId());
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
        teacherResponseDTO.setUserid(teacher.getUser().getId());
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
        parentResponseDTO.setUserid(parent.getUser().getId());
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
        schoolStudentResponseDTO.setUserid(schoolStudent.getUser().getId());
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


    public GroupResponseDTO GroupToDto(Group group)
    {
        GroupResponseDTO groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setId(group.getId());
        groupResponseDTO.setClassRoom(ClassToDto(group.getClassRoom()));
        groupResponseDTO.setGroupName(group.getGroupName());

        return groupResponseDTO;
    }

    public GroupMemberResponseDTO GroupMemberToDto(GroupMember groupMember){
        GroupMemberResponseDTO groupMemberResponseDTO = new GroupMemberResponseDTO();
        groupMemberResponseDTO.setId(groupMember.getId());
        groupMemberResponseDTO.setGroup(GroupToDto(groupMember.getGroup()));
        groupMemberResponseDTO.setSchoolStudent(SchoolStudentToDto(groupMember.getSchoolStudent()));

        return groupMemberResponseDTO;
    }

    public TeacherAssignmentResponseDTO TeacherAssignmentToDto(TeacherAssignment teacherAssignment)
    {
        TeacherAssignmentResponseDTO teacherAssignmentResponseDTO = new TeacherAssignmentResponseDTO();
        teacherAssignmentResponseDTO.setId(teacherAssignment.getId());
        teacherAssignmentResponseDTO.setTeacher(TeacherToDto(teacherAssignment.getTeacher()));
        teacherAssignmentResponseDTO.setSchoolSubject(teacherAssignment.getSchoolSubject());
        teacherAssignmentResponseDTO.setGroup(GroupToDto(teacherAssignment.getGroup()));

        return teacherAssignmentResponseDTO;
    }

    public ScheduleLessonResponseDTO ScheduleLessonToDto (ScheduleLesson scheduleLesson)
    {
        ScheduleLessonResponseDTO scheduleLessonResponseDTO = new ScheduleLessonResponseDTO();
        scheduleLessonResponseDTO.setId(scheduleLesson.getId());
        scheduleLessonResponseDTO.setGroup(GroupToDto(scheduleLesson.getGroup()));
        scheduleLessonResponseDTO.setTeacherAssignment(TeacherAssignmentToDto(scheduleLesson.getTeacherAssignment()));
        scheduleLessonResponseDTO.setQuarterInfo(scheduleLesson.getQuarterInfo());
        scheduleLessonResponseDTO.setDayNumber(scheduleLesson.getDayNumber());
        scheduleLessonResponseDTO.setLessonNumber(scheduleLesson.getLessonNumber());

        return scheduleLessonResponseDTO;
    }

    public NewsResponseDTO NewsToDto(News news){
        NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
        newsResponseDTO.setId(news.getId());
        newsResponseDTO.setTitle(news.getTitle());
        newsResponseDTO.setContent(news.getContent());
        newsResponseDTO.setDateTime(news.getDateTime());

        Administrator administrator = administratorRepository.findAdministratorByUserId(news.getOwnerUser().getId());
        SchoolStudent schoolStudent = schoolStudentRepository.findSchoolStudentByUserId(news.getOwnerUser().getId());
        Parent parent = parentRepository.findParentByUserId(news.getOwnerUser().getId());
        Teacher teacher = teacherRepository.findTeacherByUserId(news.getOwnerUser().getId());

        if (administrator != null) {
            setUserData(newsResponseDTO,
                    administrator.getUser().getId(),
                    administrator.getEducationalInstitution().getId(),
                    administrator.getFirstName(),
                    administrator.getLastName(),
                    administrator.getPatronymic());
        }
        else if (schoolStudent != null) {
            setUserData(newsResponseDTO,
                    schoolStudent.getUser().getId(),
                    schoolStudent.getEducationalInstitution().getId(),
                    schoolStudent.getFirstName(),
                    schoolStudent.getLastName(),
                    schoolStudent.getPatronymic());
        }
        else if (parent != null) {
            setUserData(newsResponseDTO,
                    parent.getUser().getId(),
                    parent.getEducationalInstitution().getId(),
                    parent.getFirstName(),
                    parent.getLastName(),
                    parent.getPatronymic());
        }
        else if (teacher != null) {
            setUserData(newsResponseDTO,
                    teacher.getUser().getId(),
                    teacher.getEducationalInstitution().getId(),
                    teacher.getFirstName(),
                    teacher.getLastName(),
                    teacher.getPatronymic());
        }

        return newsResponseDTO;
    }
    private void setUserData(NewsResponseDTO dto,
                             Long userId,
                             Long educationId,
                             String firstName,
                             String lastName,
                             String patronymic) {
        dto.setUserId(userId);
        dto.setEducationId(educationId);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setPatronymic(patronymic);
    }
}
