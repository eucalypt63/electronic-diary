package com.example.postgresql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PageControl {
//@RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @RequiredRoles({"Main admin", "Local admin"})
    @GetMapping("/adminSettings")
    public String adminSettings(HttpSession session) {
        return "adminSettings";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/profileSchoolStudent")
    public String getProfileSchoolStudent(@RequestParam String id) {
        return "profile/profileSchoolStudent";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/profileTeacher")
    public String getProfileTeacher(@RequestParam String id) {
        return "profile/profileTeacher";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/profileParent")
    public String getProfileParent(@RequestParam String id) {
        return "profile/profileParent";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/profileAdministrator")
    public String getProfileAdministrator(@RequestParam String id) {
        return "profile/profileAdministrator";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/parentPage")
    public String getParentPage(@RequestParam String id) {
        return "lists/listParents";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/parentsSchoolStudents")
    public String getParentsSchoolStudents(@RequestParam String id) {
        return "lists/listParentsSchoolStudents";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/classPage")
    public String getClassPage(@RequestParam String id) {
        return "lists/classPage";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/classSchedule")
    public String getClassSchedule(@RequestParam String id) {
        return "classSchedule";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/schoolStudentSchedule")
    public String getSchoolStudentSchedule(@RequestParam String id) {
        return "schoolStudentSchedule";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/teacherSchedule")
    public String getTeacherScheduleSchedule(@RequestParam String id) {
        return "teacherSchedule";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/classGroups")
    public String getClassGroups(@RequestParam String id) {
        return "classGroups";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher"})
    @GetMapping("/groupGradebook")
    public String getGroupGradebook(@RequestParam String teacherAssignmentId, @RequestParam String quarterId) {
        return "groupGradebook";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher"})
    @GetMapping("/groupQuarterResult")
    public String getGroupQuarterResult(@RequestParam String teacherAssignmentId) {
        return "groupQuarterResult";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/schoolStudentDiary")
    public String getSchoolStudentDiary(@RequestParam String id) {
        return "schoolStudentDiary";
    }

    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @GetMapping("/schoolStudentQuarterResult")
    public String getSchoolStudentQuarterResult(@RequestParam String id) {
        return "schoolStudentQuarterResult";
    }
    @GetMapping("/test")
    public String getTest() {
        return "test";
    }
}
