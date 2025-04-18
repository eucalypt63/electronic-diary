package com.example.postgresql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageControl {
    @GetMapping("/profileSchoolStudent")
    public String getProfileSchoolStudent(@RequestParam String id) {
        return "profile/profileSchoolStudent";
    }

    @GetMapping("/profileTeacher")
    public String getProfileTeacher(@RequestParam String id) {
        return "profile/profileTeacher";
    }

    @GetMapping("/profileParent")
    public String getProfileParent(@RequestParam String id) {
        return "profile/profileParent";
    }

    @GetMapping("/profileAdministrator")
    public String getProfileAdministrator(@RequestParam String id) {
        return "profile/profileAdministrator";
    }

    @GetMapping("/parentPage")
    public String getParentPage(@RequestParam String id) {
        return "lists/listParents";
    }

    @GetMapping("/parentsSchoolStudents")
    public String getParentsSchoolStudents(@RequestParam String id) {
        return "lists/listParentsSchoolStudents";
    }

    @GetMapping("/classPage")
    public String getClassPage(@RequestParam String id) {
        return "lists/classPage";
    }

    @GetMapping("/classSchedule")
    public String getClassSchedule(@RequestParam String id) {
        return "classSchedule";
    }

    @GetMapping("/classGroups")
    public String getClassGroups(@RequestParam String id) {
        return "classGroups";
    }

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }
}
