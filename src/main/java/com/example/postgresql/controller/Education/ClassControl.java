package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.ClassDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClassControl {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassService classService;


    @GetMapping("/getClasses")
    @ResponseBody
    public ResponseEntity<List<Class>> getClasses(@RequestParam Long schoolId) {
        List<Class> classes = classService.getAllClasses()
                .stream()
                .filter(cl -> cl.getTeacher().getUser().getEducationalInstitution().getId().equals(schoolId))
                .collect(Collectors.toList());

        if (classes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(classes);
        }

        return ResponseEntity.ok(classes);
    }

    @PostMapping("/addClass")
    @ResponseBody
    public ResponseEntity<String> addClass(@RequestBody ClassDTO classDTO) {
        System.out.println("Данные учителя: " + classDTO);
        Teacher teacher = teacherService.findTeacherById(classDTO.getTeacherId());
        Class cl = new Class(classDTO.getName(), teacher);

        classService.saveClass(cl);
        return ResponseEntity.ok("{\"message\": \"Класс успешно добавлен\"}");
    }

    @DeleteMapping("/deleteClass")
    public ResponseEntity<Void> deleteClass(@RequestParam("id") Long id) {
        classService.deleteClassById(id);
        return ResponseEntity.ok().build();
    }
}
