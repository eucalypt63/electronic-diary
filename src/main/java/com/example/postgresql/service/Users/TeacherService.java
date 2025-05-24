package com.example.postgresql.service.Users;

import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Teacher;
import com.example.postgresql.repository.TeacherAssignmentRepository;
import com.example.postgresql.repository.Users.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherAssignmentRepository teacherAssignmentRepository;


    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }
    public Teacher findTeacherByUserId(Long id) {
        return teacherRepository.findTeacherByUserId(id);
    }
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }
    public List<Teacher> findTeacherByEducationalInstitutionId(Long schoolId) {
        return teacherRepository.findTeacherByEducationalInstitutionId(schoolId);
    }

    public TeacherAssignment findTeacherAssignmentById(Long id) {
        return teacherAssignmentRepository.findById(id).orElse(null);
    }
    public List<Teacher> findTeachersBySchoolId(Long schoolId, List<Long> assignedTeacherIds) {
        List<Teacher> teachers = teacherRepository.findTeacherByEducationalInstitutionId(schoolId);
        return teachers.stream()
                .filter(teacher -> !assignedTeacherIds.contains(teacher.getId()))
                .collect(Collectors.toList());
    }

    public TeacherAssignment findTeacherAssignmentByGroupIdAndSchoolSubjectIdAndTeacherId(
            Long groupId,
            Long schoolSubjectId,
            Long teacherId){
        return teacherAssignmentRepository.findTeacherAssignmentByGroupIdAndSchoolSubjectIdAndTeacherId(groupId, schoolSubjectId, teacherId);
    }

    public List<TeacherAssignment> findTeacherAssignmentByTeacherId(Long teacherId){
        return teacherAssignmentRepository.findTeacherAssignmentByTeacherId(teacherId);
    }

    public void saveTeacherAssignment(TeacherAssignment teacherAssignment) {
        teacherAssignmentRepository.save(teacherAssignment);
    }
}
