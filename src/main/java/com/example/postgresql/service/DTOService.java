package com.example.postgresql.service;

import com.example.postgresql.DTO.ResponseDTO.*;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupMemberResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsCommentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.*;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Education.Message;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.model.Education.News.NewsComment;
import com.example.postgresql.model.Education.UserComment;
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
            newsResponseDTO.setUserId(administrator.getUser().getId());
            newsResponseDTO.setEducationId(administrator.getEducationalInstitution().getId());
            newsResponseDTO.setFirstName(administrator.getFirstName());
            newsResponseDTO.setLastName(administrator.getLastName());
            newsResponseDTO.setPatronymic(administrator.getPatronymic());
        }
        else if (schoolStudent != null) {
            newsResponseDTO.setUserId(schoolStudent.getUser().getId());
            newsResponseDTO.setEducationId(schoolStudent.getEducationalInstitution().getId());
            newsResponseDTO.setFirstName(schoolStudent.getFirstName());
            newsResponseDTO.setLastName(schoolStudent.getLastName());
            newsResponseDTO.setPatronymic(schoolStudent.getPatronymic());
        }
        else if (parent != null) {
            newsResponseDTO.setUserId(parent.getUser().getId());
            newsResponseDTO.setEducationId(parent.getEducationalInstitution().getId());
            newsResponseDTO.setFirstName(parent.getFirstName());
            newsResponseDTO.setLastName(parent.getLastName());
            newsResponseDTO.setPatronymic(parent.getPatronymic());
        }
        else if (teacher != null) {
            newsResponseDTO.setUserId(teacher.getUser().getId());
            newsResponseDTO.setEducationId(teacher.getEducationalInstitution().getId());
            newsResponseDTO.setFirstName(teacher.getFirstName());
            newsResponseDTO.setLastName(teacher.getLastName());
            newsResponseDTO.setPatronymic(teacher.getPatronymic());
        }

        return newsResponseDTO;
    }

    public NewsCommentResponseDTO NewsCommentToDto(NewsComment newsComment){
        NewsCommentResponseDTO newsCommentResponseDTO = new NewsCommentResponseDTO();
        newsCommentResponseDTO.setId(newsComment.getId());
        newsCommentResponseDTO.setContent(newsComment.getContent());
        newsCommentResponseDTO.setDateTime(newsComment.getDateTime());
        newsCommentResponseDTO.setNewsResponseDTO(NewsToDto(newsComment.getNews()));

        Administrator administrator = administratorRepository.findAdministratorByUserId(newsComment.getUser().getId());
        SchoolStudent schoolStudent = schoolStudentRepository.findSchoolStudentByUserId(newsComment.getUser().getId());
        Parent parent = parentRepository.findParentByUserId(newsComment.getUser().getId());
        Teacher teacher = teacherRepository.findTeacherByUserId(newsComment.getUser().getId());

        if (administrator != null) {
            newsCommentResponseDTO.setUserId(administrator.getUser().getId());
            newsCommentResponseDTO.setEducationId(administrator.getEducationalInstitution().getId());
            newsCommentResponseDTO.setFirstName(administrator.getFirstName());
            newsCommentResponseDTO.setLastName(administrator.getLastName());
            newsCommentResponseDTO.setPatronymic(administrator.getPatronymic());
        }
        else if (schoolStudent != null) {
            newsCommentResponseDTO.setUserId(schoolStudent.getUser().getId());
            newsCommentResponseDTO.setEducationId(schoolStudent.getEducationalInstitution().getId());
            newsCommentResponseDTO.setFirstName(schoolStudent.getFirstName());
            newsCommentResponseDTO.setLastName(schoolStudent.getLastName());
            newsCommentResponseDTO.setPatronymic(schoolStudent.getPatronymic());
        }
        else if (parent != null) {
            newsCommentResponseDTO.setUserId(parent.getUser().getId());
            newsCommentResponseDTO.setEducationId(parent.getEducationalInstitution().getId());
            newsCommentResponseDTO.setFirstName(parent.getFirstName());
            newsCommentResponseDTO.setLastName(parent.getLastName());
            newsCommentResponseDTO.setPatronymic(parent.getPatronymic());
        }
        else if (teacher != null) {
            newsCommentResponseDTO.setUserId(teacher.getUser().getId());
            newsCommentResponseDTO.setEducationId(teacher.getEducationalInstitution().getId());
            newsCommentResponseDTO.setFirstName(teacher.getFirstName());
            newsCommentResponseDTO.setLastName(teacher.getLastName());
            newsCommentResponseDTO.setPatronymic(teacher.getPatronymic());
        }

        return newsCommentResponseDTO;
    }

    public UserCommentResponseDTO UserCommentToDto(UserComment userComment){
        UserCommentResponseDTO userCommentResponseDTO = new UserCommentResponseDTO();
        userCommentResponseDTO.setId(userComment.getId());
        userCommentResponseDTO.setContent(userComment.getContent());
        userCommentResponseDTO.setDateTime(userComment.getDateTime());

        Administrator senderAdministrator = administratorRepository.findAdministratorByUserId(userComment.getSenderUser().getId());
        SchoolStudent senderSchoolStudent = schoolStudentRepository.findSchoolStudentByUserId(userComment.getSenderUser().getId());
        Parent senderParent = parentRepository.findParentByUserId(userComment.getSenderUser().getId());
        Teacher senderTeacher = teacherRepository.findTeacherByUserId(userComment.getSenderUser().getId());

        Administrator getterAdministrator = administratorRepository.findAdministratorByUserId(userComment.getGetterUser().getId());
        SchoolStudent getterSchoolStudent = schoolStudentRepository.findSchoolStudentByUserId(userComment.getGetterUser().getId());
        Parent getterParent = parentRepository.findParentByUserId(userComment.getGetterUser().getId());
        Teacher getterTeacher = teacherRepository.findTeacherByUserId(userComment.getGetterUser().getId());

        if (senderAdministrator != null) {
            userCommentResponseDTO.setSenderUserId(senderAdministrator.getUser().getId());
            userCommentResponseDTO.setSenderEducationId(senderAdministrator.getEducationalInstitution().getId());
            userCommentResponseDTO.setSenderFirstName(senderAdministrator.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderAdministrator.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderAdministrator.getPatronymic());
        }
        else if (senderSchoolStudent != null) {
            userCommentResponseDTO.setSenderUserId(senderSchoolStudent.getUser().getId());
            userCommentResponseDTO.setSenderEducationId(senderSchoolStudent.getEducationalInstitution().getId());
            userCommentResponseDTO.setSenderFirstName(senderSchoolStudent.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderSchoolStudent.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderSchoolStudent.getPatronymic());
        }
        else if (senderParent != null) {
            userCommentResponseDTO.setSenderUserId(senderParent.getUser().getId());
            userCommentResponseDTO.setSenderEducationId(senderParent.getEducationalInstitution().getId());
            userCommentResponseDTO.setSenderFirstName(senderParent.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderParent.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderParent.getPatronymic());
        }
        else if (senderTeacher != null) {
            userCommentResponseDTO.setSenderUserId(senderTeacher.getUser().getId());
            userCommentResponseDTO.setSenderEducationId(senderTeacher.getEducationalInstitution().getId());
            userCommentResponseDTO.setSenderFirstName(senderTeacher.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderTeacher.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderTeacher.getPatronymic());
        }

        if (getterAdministrator != null) {
            userCommentResponseDTO.setGetterUserId(getterAdministrator.getUser().getId());
            userCommentResponseDTO.setGetterEducationId(getterAdministrator.getEducationalInstitution().getId());
            userCommentResponseDTO.setGetterFirstName(getterAdministrator.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterAdministrator.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterAdministrator.getPatronymic());
        }
        else if (getterSchoolStudent != null) {
            userCommentResponseDTO.setGetterUserId(getterSchoolStudent.getUser().getId());
            userCommentResponseDTO.setGetterEducationId(getterSchoolStudent.getEducationalInstitution().getId());
            userCommentResponseDTO.setGetterFirstName(getterSchoolStudent.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterSchoolStudent.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterSchoolStudent.getPatronymic());
        }
        else if (getterParent != null) {
            userCommentResponseDTO.setGetterUserId(getterParent.getUser().getId());
            userCommentResponseDTO.setGetterEducationId(getterParent.getEducationalInstitution().getId());
            userCommentResponseDTO.setGetterFirstName(getterParent.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterParent.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterParent.getPatronymic());
        }
        else if (getterTeacher != null) {
            userCommentResponseDTO.setGetterUserId(getterTeacher.getUser().getId());
            userCommentResponseDTO.setGetterEducationId(getterTeacher.getEducationalInstitution().getId());
            userCommentResponseDTO.setGetterFirstName(getterTeacher.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterTeacher.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterTeacher.getPatronymic());
        }

        return userCommentResponseDTO;
    }

    public MessageResponseDTO MessageToDto(Message message){
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        messageResponseDTO.setId(message.getId());
        messageResponseDTO.setMessage(message.getMessage());
        messageResponseDTO.setDateTime(message.getDateTime());

        Administrator senderAdministrator = administratorRepository.findAdministratorByUserId(message.getSenderUser().getId());
        SchoolStudent senderSchoolStudent = schoolStudentRepository.findSchoolStudentByUserId(message.getSenderUser().getId());
        Parent senderParent = parentRepository.findParentByUserId(message.getSenderUser().getId());
        Teacher senderTeacher = teacherRepository.findTeacherByUserId(message.getSenderUser().getId());

        Administrator getterAdministrator = administratorRepository.findAdministratorByUserId(message.getGetterUser().getId());
        SchoolStudent getterSchoolStudent = schoolStudentRepository.findSchoolStudentByUserId(message.getGetterUser().getId());
        Parent getterParent = parentRepository.findParentByUserId(message.getGetterUser().getId());
        Teacher getterTeacher = teacherRepository.findTeacherByUserId(message.getGetterUser().getId());

        if (senderAdministrator != null) {
            messageResponseDTO.setSenderUserId(senderAdministrator.getUser().getId());
            messageResponseDTO.setSenderEducationId(senderAdministrator.getEducationalInstitution().getId());
            messageResponseDTO.setSenderFirstName(senderAdministrator.getFirstName());
            messageResponseDTO.setSenderLastName(senderAdministrator.getLastName());
            messageResponseDTO.setSenderPatronymic(senderAdministrator.getPatronymic());
        }
        else if (senderSchoolStudent != null) {
            messageResponseDTO.setSenderUserId(senderSchoolStudent.getUser().getId());
            messageResponseDTO.setSenderEducationId(senderSchoolStudent.getEducationalInstitution().getId());
            messageResponseDTO.setSenderFirstName(senderSchoolStudent.getFirstName());
            messageResponseDTO.setSenderLastName(senderSchoolStudent.getLastName());
            messageResponseDTO.setSenderPatronymic(senderSchoolStudent.getPatronymic());
        }
        else if (senderParent != null) {
            messageResponseDTO.setSenderUserId(senderParent.getUser().getId());
            messageResponseDTO.setSenderEducationId(senderParent.getEducationalInstitution().getId());
            messageResponseDTO.setSenderFirstName(senderParent.getFirstName());
            messageResponseDTO.setSenderLastName(senderParent.getLastName());
            messageResponseDTO.setSenderPatronymic(senderParent.getPatronymic());
        }
        else if (senderTeacher != null) {
            messageResponseDTO.setSenderUserId(senderTeacher.getUser().getId());
            messageResponseDTO.setSenderEducationId(senderTeacher.getEducationalInstitution().getId());
            messageResponseDTO.setSenderFirstName(senderTeacher.getFirstName());
            messageResponseDTO.setSenderLastName(senderTeacher.getLastName());
            messageResponseDTO.setSenderPatronymic(senderTeacher.getPatronymic());
        }

        if (getterAdministrator != null) {
            messageResponseDTO.setGetterUserId(getterAdministrator.getUser().getId());
            messageResponseDTO.setGetterEducationId(getterAdministrator.getEducationalInstitution().getId());
            messageResponseDTO.setGetterFirstName(getterAdministrator.getFirstName());
            messageResponseDTO.setGetterLastName(getterAdministrator.getLastName());
            messageResponseDTO.setGetterPatronymic(getterAdministrator.getPatronymic());
        }
        else if (getterSchoolStudent != null) {
            messageResponseDTO.setGetterUserId(getterSchoolStudent.getUser().getId());
            messageResponseDTO.setGetterEducationId(getterSchoolStudent.getEducationalInstitution().getId());
            messageResponseDTO.setGetterFirstName(getterSchoolStudent.getFirstName());
            messageResponseDTO.setGetterLastName(getterSchoolStudent.getLastName());
            messageResponseDTO.setGetterPatronymic(getterSchoolStudent.getPatronymic());
        }
        else if (getterParent != null) {
            messageResponseDTO.setGetterUserId(getterParent.getUser().getId());
            messageResponseDTO.setGetterEducationId(getterParent.getEducationalInstitution().getId());
            messageResponseDTO.setGetterFirstName(getterParent.getFirstName());
            messageResponseDTO.setGetterLastName(getterParent.getLastName());
            messageResponseDTO.setGetterPatronymic(getterParent.getPatronymic());
        }
        else if (getterTeacher != null) {
            messageResponseDTO.setGetterUserId(getterTeacher.getUser().getId());
            messageResponseDTO.setGetterEducationId(getterTeacher.getEducationalInstitution().getId());
            messageResponseDTO.setGetterFirstName(getterTeacher.getFirstName());
            messageResponseDTO.setGetterLastName(getterTeacher.getLastName());
            messageResponseDTO.setGetterPatronymic(getterTeacher.getPatronymic());
        }

        return messageResponseDTO;
    }
}
