package com.example.postgresql.service;

import com.example.postgresql.DTO.ResponseDTO.*;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookAttendanceResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookDayResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookScoreResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupMemberResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsCommentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.*;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.Gradebook.GradebookAttendance;
import com.example.postgresql.model.Education.Gradebook.GradebookDay;
import com.example.postgresql.model.Education.Gradebook.GradebookScore;
import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Education.Message;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.model.Education.News.NewsComment;
import com.example.postgresql.model.Education.Notification;
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
        schoolStudentResponseDTO.setClassRoom(ClassToDto(schoolStudent.getClassRoom()));
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
        newsResponseDTO.setEducationId(news.getEducationalInstitution().getId());
        newsResponseDTO.setUserId(news.getOwnerUser().getId());
        newsResponseDTO.setTitle(news.getTitle());
        newsResponseDTO.setContent(news.getContent());
        newsResponseDTO.setDateTime(news.getDateTime());

        Administrator administrator = administratorRepository.findAdministratorByUserId(news.getOwnerUser().getId());
        SchoolStudent schoolStudent = schoolStudentRepository.findSchoolStudentByUserId(news.getOwnerUser().getId());
        Parent parent = parentRepository.findParentByUserId(news.getOwnerUser().getId());
        Teacher teacher = teacherRepository.findTeacherByUserId(news.getOwnerUser().getId());

        if (administrator != null) {
            newsResponseDTO.setFirstName(administrator.getFirstName());
            newsResponseDTO.setLastName(administrator.getLastName());
            newsResponseDTO.setPatronymic(administrator.getPatronymic());
        }
        else if (schoolStudent != null) {
            newsResponseDTO.setFirstName(schoolStudent.getFirstName());
            newsResponseDTO.setLastName(schoolStudent.getLastName());
            newsResponseDTO.setPatronymic(schoolStudent.getPatronymic());
        }
        else if (parent != null) {
            newsResponseDTO.setFirstName(parent.getFirstName());
            newsResponseDTO.setLastName(parent.getLastName());
            newsResponseDTO.setPatronymic(parent.getPatronymic());
        }
        else if (teacher != null) {
            newsResponseDTO.setFirstName(teacher.getFirstName());
            newsResponseDTO.setLastName(teacher.getLastName());
            newsResponseDTO.setPatronymic(teacher.getPatronymic());
        } else {
            newsResponseDTO.setFirstName("Оповещение");
            newsResponseDTO.setLastName("Системное");
        }

        return newsResponseDTO;
    }

    public NewsCommentResponseDTO NewsCommentToDto(NewsComment newsComment){
        NewsCommentResponseDTO newsCommentResponseDTO = new NewsCommentResponseDTO();
        newsCommentResponseDTO.setId(newsComment.getId());
        newsCommentResponseDTO.setUserId(newsComment.getUser().getId());
        newsCommentResponseDTO.setContent(newsComment.getContent());
        newsCommentResponseDTO.setDateTime(newsComment.getDateTime());
        newsCommentResponseDTO.setNewsResponseDTO(NewsToDto(newsComment.getNews()));

        Administrator administrator = administratorRepository.findAdministratorByUserId(newsComment.getUser().getId());
        SchoolStudent schoolStudent = schoolStudentRepository.findSchoolStudentByUserId(newsComment.getUser().getId());
        Parent parent = parentRepository.findParentByUserId(newsComment.getUser().getId());
        Teacher teacher = teacherRepository.findTeacherByUserId(newsComment.getUser().getId());

        if (administrator != null) {
            newsCommentResponseDTO.setFirstName(administrator.getFirstName());
            newsCommentResponseDTO.setLastName(administrator.getLastName());
            newsCommentResponseDTO.setPatronymic(administrator.getPatronymic());
        }
        else if (schoolStudent != null) {
            newsCommentResponseDTO.setFirstName(schoolStudent.getFirstName());
            newsCommentResponseDTO.setLastName(schoolStudent.getLastName());
            newsCommentResponseDTO.setPatronymic(schoolStudent.getPatronymic());
        }
        else if (parent != null) {
            newsCommentResponseDTO.setFirstName(parent.getFirstName());
            newsCommentResponseDTO.setLastName(parent.getLastName());
            newsCommentResponseDTO.setPatronymic(parent.getPatronymic());
        }
        else if (teacher != null) {
            newsCommentResponseDTO.setFirstName(teacher.getFirstName());
            newsCommentResponseDTO.setLastName(teacher.getLastName());
            newsCommentResponseDTO.setPatronymic(teacher.getPatronymic());
        } else {
            newsCommentResponseDTO.setFirstName("Оповещение");
            newsCommentResponseDTO.setLastName("Системное");
        }

        return newsCommentResponseDTO;
    }

    public UserCommentResponseDTO UserCommentToDto(UserComment userComment){
        UserCommentResponseDTO userCommentResponseDTO = new UserCommentResponseDTO();
        userCommentResponseDTO.setId(userComment.getId());
        userCommentResponseDTO.setGetterUserId(userComment.getGetterUser().getId());
        userCommentResponseDTO.setSenderUserId(userComment.getSenderUser().getId());
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
            userCommentResponseDTO.setSenderFirstName(senderAdministrator.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderAdministrator.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderAdministrator.getPatronymic());
        }
        else if (senderSchoolStudent != null) {
            userCommentResponseDTO.setSenderFirstName(senderSchoolStudent.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderSchoolStudent.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderSchoolStudent.getPatronymic());
        }
        else if (senderParent != null) {
            userCommentResponseDTO.setSenderFirstName(senderParent.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderParent.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderParent.getPatronymic());
        }
        else if (senderTeacher != null) {
            userCommentResponseDTO.setSenderFirstName(senderTeacher.getFirstName());
            userCommentResponseDTO.setSenderLastName(senderTeacher.getLastName());
            userCommentResponseDTO.setSenderPatronymic(senderTeacher.getPatronymic());
        } else {
            userCommentResponseDTO.setSenderFirstName("Оповещение");
            userCommentResponseDTO.setSenderLastName("Системное");
        }

        if (getterAdministrator != null) {
            userCommentResponseDTO.setGetterFirstName(getterAdministrator.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterAdministrator.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterAdministrator.getPatronymic());
        }
        else if (getterSchoolStudent != null) {
            userCommentResponseDTO.setGetterFirstName(getterSchoolStudent.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterSchoolStudent.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterSchoolStudent.getPatronymic());
        }
        else if (getterParent != null) {
            userCommentResponseDTO.setGetterFirstName(getterParent.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterParent.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterParent.getPatronymic());
        }
        else if (getterTeacher != null) {
            userCommentResponseDTO.setGetterFirstName(getterTeacher.getFirstName());
            userCommentResponseDTO.setGetterLastName(getterTeacher.getLastName());
            userCommentResponseDTO.setGetterPatronymic(getterTeacher.getPatronymic());
        }  else {
            userCommentResponseDTO.setGetterFirstName("Оповещение");
            userCommentResponseDTO.setGetterLastName("Системное");
        }

        return userCommentResponseDTO;
    }

    public MessageResponseDTO MessageToDto(Message message){
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        messageResponseDTO.setId(message.getId());
        messageResponseDTO.setGetterUserId(message.getGetterUser().getId());
        messageResponseDTO.setSenderUserId(message.getSenderUser().getId());
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
            messageResponseDTO.setSenderFirstName(senderAdministrator.getFirstName());
            messageResponseDTO.setSenderLastName(senderAdministrator.getLastName());
            messageResponseDTO.setSenderPatronymic(senderAdministrator.getPatronymic());
        }
        else if (senderSchoolStudent != null) {
            messageResponseDTO.setSenderFirstName(senderSchoolStudent.getFirstName());
            messageResponseDTO.setSenderLastName(senderSchoolStudent.getLastName());
            messageResponseDTO.setSenderPatronymic(senderSchoolStudent.getPatronymic());
        }
        else if (senderParent != null) {
            messageResponseDTO.setSenderFirstName(senderParent.getFirstName());
            messageResponseDTO.setSenderLastName(senderParent.getLastName());
            messageResponseDTO.setSenderPatronymic(senderParent.getPatronymic());
        }
        else if (senderTeacher != null) {
            messageResponseDTO.setSenderFirstName(senderTeacher.getFirstName());
            messageResponseDTO.setSenderLastName(senderTeacher.getLastName());
            messageResponseDTO.setSenderPatronymic(senderTeacher.getPatronymic());
        } else {
            messageResponseDTO.setSenderFirstName("Оповещение");
            messageResponseDTO.setSenderLastName("Системное");
        }

        if (getterAdministrator != null) {
            messageResponseDTO.setGetterFirstName(getterAdministrator.getFirstName());
            messageResponseDTO.setGetterLastName(getterAdministrator.getLastName());
            messageResponseDTO.setGetterPatronymic(getterAdministrator.getPatronymic());
        }
        else if (getterSchoolStudent != null) {
            messageResponseDTO.setGetterFirstName(getterSchoolStudent.getFirstName());
            messageResponseDTO.setGetterLastName(getterSchoolStudent.getLastName());
            messageResponseDTO.setGetterPatronymic(getterSchoolStudent.getPatronymic());
        }
        else if (getterParent != null) {
            messageResponseDTO.setGetterFirstName(getterParent.getFirstName());
            messageResponseDTO.setGetterLastName(getterParent.getLastName());
            messageResponseDTO.setGetterPatronymic(getterParent.getPatronymic());
        }
        else if (getterTeacher != null) {
            messageResponseDTO.setGetterFirstName(getterTeacher.getFirstName());
            messageResponseDTO.setGetterLastName(getterTeacher.getLastName());
            messageResponseDTO.setGetterPatronymic(getterTeacher.getPatronymic());
        } else {
            messageResponseDTO.setGetterFirstName("Оповещение");
            messageResponseDTO.setGetterLastName("Системное");
        }

        return messageResponseDTO;
    }

    public GradebookDayResponseDTO GradebookDayToDto(GradebookDay gradebookDay){
        GradebookDayResponseDTO gradebookDayResponseDTO = new GradebookDayResponseDTO();
        gradebookDayResponseDTO.setId(gradebookDay.getId());
        gradebookDayResponseDTO.setScheduleLesson(ScheduleLessonToDto(gradebookDay.getScheduleLesson()));
        gradebookDayResponseDTO.setTopic(gradebookDay.getTopic());
        gradebookDayResponseDTO.setHomework(gradebookDay.getHomework());
        gradebookDayResponseDTO.setDateTime(gradebookDay.getDateTime());

        return gradebookDayResponseDTO;
    }

    public GradebookAttendanceResponseDTO GradebookAttendanceToDto(GradebookAttendance gradebookAttendance){
        GradebookAttendanceResponseDTO gradebookAttendanceResponseDTO = new GradebookAttendanceResponseDTO();
        gradebookAttendanceResponseDTO.setId(gradebookAttendance.getId());
        gradebookAttendanceResponseDTO.setGradebookDay(GradebookDayToDto(gradebookAttendance.getGradebookDay()));
        gradebookAttendanceResponseDTO.setSchoolStudent(SchoolStudentToDto(gradebookAttendance.getSchoolStudent()));
        return gradebookAttendanceResponseDTO;
    }

    public GradebookScoreResponseDTO GradebookScoreToDto(GradebookScore gradebookAttendance){
        GradebookScoreResponseDTO gradebookScoreResponseDTO = new GradebookScoreResponseDTO();
        gradebookScoreResponseDTO.setId(gradebookAttendance.getId());
        gradebookScoreResponseDTO.setGradebookDay(GradebookDayToDto(gradebookAttendance.getGradebookDay()));
        gradebookScoreResponseDTO.setSchoolStudent(SchoolStudentToDto(gradebookAttendance.getSchoolStudent()));
        gradebookScoreResponseDTO.setScore(gradebookAttendance.getScore());
        return gradebookScoreResponseDTO;
    }

    public NotificationResponseDTO NotificationToDto(Notification notification){
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();
        notificationResponseDTO.setId(notification.getId());
        notificationResponseDTO.setUserId(notification.getUser().getId());
        notificationResponseDTO.setTitle(notification.getTitle());
        notificationResponseDTO.setContent(notification.getContent());
        notificationResponseDTO.setLink(notification.getLink());
        notificationResponseDTO.setDateTime(notification.getDateTime());
        return notificationResponseDTO;
    }
}
