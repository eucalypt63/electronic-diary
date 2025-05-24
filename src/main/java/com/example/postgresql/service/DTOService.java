package com.example.postgresql.service;

import com.example.postgresql.DTO.ResponseDTO.*;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookAttendanceResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookDayResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Gradebook.GradebookScoreResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupMemberResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Group.GroupResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.News.NewsResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Schedule.ScheduleLessonResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.*;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.Gradebook.GradebookAttendance;
import com.example.postgresql.model.Education.Gradebook.GradebookDay;
import com.example.postgresql.model.Education.Gradebook.GradebookScore;
import com.example.postgresql.model.Education.Gradebook.ScheduleLesson;
import com.example.postgresql.model.Education.Group.GroupMember;
import com.example.postgresql.model.Education.News.News;
import com.example.postgresql.model.Education.Notification;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Administrations.Administrations;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Users.Student.Parent;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.model.Users.Student.StudentParent;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.repository.Users.Administrations.AdministrationsRepository;
import com.example.postgresql.repository.Users.Student.ParentRepository;
import com.example.postgresql.repository.Users.Student.SchoolStudentRepository;
import com.example.postgresql.repository.Users.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DTOService {
    @Autowired
    private AdministrationsRepository administrationsRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SchoolStudentRepository schoolStudentRepository;

    public AdministratorResponseDTO AdministratorToDto(Administrations administrations)
    {
        AdministratorResponseDTO administratorResponseDTO = new AdministratorResponseDTO();
        administratorResponseDTO.setId(administrations.getId());
        administratorResponseDTO.setUserid(administrations.getUser().getId());
        administratorResponseDTO.setEducationalInstitution(administrations.getEducationalInstitution());
        administratorResponseDTO.setFirstName(administrations.getFirstName());
        administratorResponseDTO.setLastName(administrations.getLastName());
        administratorResponseDTO.setPatronymic(administrations.getPatronymic());
        administratorResponseDTO.setPathImage(administrations.getPathImage());
        administratorResponseDTO.setEmail(administrations.getEmail());
        administratorResponseDTO.setPhoneNumber(administrations.getPhoneNumber());

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
        scheduleLessonResponseDTO.setRoom(scheduleLesson.getRoom());

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

        Administrations administrations = administrationsRepository.findAdministrationsByUserId(news.getOwnerUser().getId());
        SchoolStudent schoolStudent = schoolStudentRepository.findSchoolStudentByUserId(news.getOwnerUser().getId());
        Parent parent = parentRepository.findParentByUserId(news.getOwnerUser().getId());
        Teacher teacher = teacherRepository.findTeacherByUserId(news.getOwnerUser().getId());

        if (administrations != null) {
            newsResponseDTO.setFirstName(administrations.getFirstName());
            newsResponseDTO.setLastName(administrations.getLastName());
            newsResponseDTO.setPatronymic(administrations.getPatronymic());
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
