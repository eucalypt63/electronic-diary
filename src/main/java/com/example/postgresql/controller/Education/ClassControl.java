package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.ClassRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.ClassResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.TeacherAssignmentResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.Users.TeacherResponseDTO;
import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Education.Notification;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Education.GroupService;
import com.example.postgresql.service.Education.NotificationService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClassControl {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassService classService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DTOService dtoService;

    //Получить классы по id школы
    @GetMapping("/getClasses")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<ClassResponseDTO>> getClasses(@RequestParam Long schoolId) {
        List<Class> classes = classService.findAllByTeacherEducationalInstitutionId(schoolId);

        List<ClassResponseDTO> classResponseDTOS = new ArrayList<>();
        for (Class cl : classes)
        {
            ClassResponseDTO classResponseDTO = dtoService.ClassToDto(cl);
            classResponseDTOS.add(classResponseDTO);
        }

        if (classResponseDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(classResponseDTOS);
    }

    //Получить класс по id учителя
    @GetMapping("/findClassByTeacherId")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<ClassResponseDTO> findClassByTeacherId(@RequestParam Long id) {
        Class cl = classService.findClassByTeacherId(id);
        ClassResponseDTO classResponseDTO = dtoService.ClassToDto(cl);

        return ResponseEntity.ok(classResponseDTO);
    }

    // Получить учителя по id класс
    @GetMapping("/getTeacherOfClass")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<TeacherResponseDTO> getTeacherOfClass(@RequestParam Long id) {
        Class cl = classService.findClassById(id);
        Teacher teacher = cl.getTeacher();
        TeacherResponseDTO teacherResponseDTO = dtoService.TeacherToDto(teacher);

        return ResponseEntity.ok(teacherResponseDTO);
    }

    @GetMapping("/getTeacherAssignment")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<TeacherAssignmentResponseDTO> getTeacherAssignment(@RequestParam Long id) {
        TeacherAssignmentResponseDTO teacherAssignmentResponseDTO = dtoService.TeacherAssignmentToDto(teacherService.findTeacherAssignmentById(id));

        return ResponseEntity.ok(teacherAssignmentResponseDTO);
    }

    // Создать новый класс
    @PostMapping("/addClass")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> addClass(@RequestBody ClassRequestDTO classRequestDTO) {
        Teacher teacher = teacherService.findTeacherById(classRequestDTO.getTeacherId());
        Class cl = new Class(classRequestDTO.getName());
        cl.setTeacher(teacher);
        classService.saveClass(cl);

        Group group = new Group("Класс");
        group.setClassRoom(cl);
        groupService.saveGroup(group);

        Notification notification = new Notification();
        notification.setUser(teacher.getUser());
        notification.setLink(
                String.format(
                        "/classPage?id=%d",
                        cl.getId()
                )
        );
        notification.setDateTime(LocalDateTime.now());
        notification.setTitle("Назначение");
        notification.setContent(
                String.format(
                        "Вы были назначены классным руководителем класса %s",
                        cl.getName()
                )
        );
        notificationService.saveNotification(notification);

        return ResponseEntity.ok("{\"message\": \"Класс успешно добавлен\"}");
    }

    //Удалить класс
    @DeleteMapping("/deleteClass")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<Void> deleteClass(@RequestParam("id") Long id) {
        classService.deleteClassById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeClass")
    @RequiredRoles({"Main admin", "Local admin"})
    @ResponseBody
    public ResponseEntity<String> changeClass(@RequestBody ClassRequestDTO classRequestDTO) {

        Class cl = classService.findClassById(classRequestDTO.getId());

        Teacher teacherOld = cl.getTeacher();

        cl.setName(classRequestDTO.getName());
        cl.setTeacher(teacherService.findTeacherById(classRequestDTO.getTeacherId()));
        classService.saveClass(cl);

        Teacher teacherNew = cl.getTeacher();

        if (teacherOld != teacherNew) {
            Notification notification = new Notification();
            notification.setUser(teacherNew.getUser());
            notification.setLink(
                    String.format(
                            "/classPage?id=%d",
                            cl.getId()
                    )
            );
            notification.setDateTime(LocalDateTime.now());
            notification.setTitle("Назначение");
            notification.setContent(
                    String.format(
                            "Вы были назначены классным руководителем класса %s",
                            cl.getName()
                    )
            );
            notificationService.saveNotification(notification);

            Notification notification2 = new Notification();
            notification2.setUser(teacherOld.getUser());
            notification2.setLink(
                    String.format(
                            "/classPage?id=%d",
                            cl.getId()
                    )
            );
            notification2.setDateTime(LocalDateTime.now());
            notification2.setTitle("Назначение");
            notification2.setContent(
                    String.format(
                            "Ваше назначение классным руководителем класса %s было отменено",
                            cl.getName()
                    )
            );
            notificationService.saveNotification(notification2);
        }

        return ResponseEntity.ok("{\"message\": \"Класс успешно обновлён\"}");

    }
}
