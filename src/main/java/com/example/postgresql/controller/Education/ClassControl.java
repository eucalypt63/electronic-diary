package com.example.postgresql.controller.Education;

import com.example.postgresql.DTO.RequestDTO.ClassRequestDTO;
import com.example.postgresql.DTO.ResponseDTO.ClassResponseDTO;
import com.example.postgresql.DTO.ResponseDTO.TeacherResponseDTO;
import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.service.DTOService;
import com.example.postgresql.service.Education.ClassService;
import com.example.postgresql.service.Users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClassControl {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassService classService;

    @Autowired
    private DTOService dtoService;

    //Получить классы по id школы
    @GetMapping("/getClasses")
    @ResponseBody
    public ResponseEntity<List<ClassResponseDTO>> getClasses(@RequestParam Long schoolId) {
        List<Class> classes = classService.getAllClasses()
                .stream()
                .filter(cl -> cl.getTeacher().getEducationalInstitution().getId().equals(schoolId))
                .collect(Collectors.toList());
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
    @ResponseBody
    public ResponseEntity<ClassResponseDTO> findClassByTeacherId(@RequestParam Long id) {
        Class cl = classService.findClassByTeacherId(id);
        ClassResponseDTO classResponseDTO = dtoService.ClassToDto(cl);

        return ResponseEntity.ok(classResponseDTO);
    }

    // Получить учителя по id класс
    @GetMapping("/getTeacherOfClass")
    @ResponseBody
    public ResponseEntity<TeacherResponseDTO> getTeacherOfClass(@RequestParam Long id) {
        Class cl = classService.findClassById(id);
        Teacher teacher = cl.getTeacher();
        TeacherResponseDTO teacherResponseDTO = dtoService.TeacherToDto(teacher);

        return ResponseEntity.ok(teacherResponseDTO);
    }

    // Создать новый класс
    @PostMapping("/addClass")
    @ResponseBody
    public ResponseEntity<String> addClass(@RequestBody ClassRequestDTO classRequestDTO) {
        System.out.println("Данные учителя: " + classRequestDTO);

        Teacher teacher = teacherService.findTeacherById(classRequestDTO.getTeacherId());
        Class cl = new Class(classRequestDTO.getName(), teacher);

        classService.saveClass(cl);
        return ResponseEntity.ok("{\"message\": \"Класс успешно добавлен\"}");
    }

    //Удалить класс
    @DeleteMapping("/deleteClass")
    public ResponseEntity<Void> deleteClass(@RequestParam("id") Long id) {
        classService.deleteClassById(id);
        return ResponseEntity.ok().build();
    }
}
