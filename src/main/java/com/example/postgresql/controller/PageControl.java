package com.example.postgresql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageControl {
    @GetMapping("/profileSchoolStudent")
    public String getProfileSchoolStudent(@RequestParam String id) {
        return "profileSchoolStudent";
    }

    @GetMapping("/profileTeacher")
    public String getProfileTeacher(@RequestParam String id) {
        return "profileTeacher";
    }

    @GetMapping("/profileParent")
    public String getProfileParent(@RequestParam String id) {
        return "profileParent";
    }

    @GetMapping("/profileAdministrator")
    public String getProfileAdministrator(@RequestParam String id) {
        return "profileAdministrator";
    }

    @GetMapping("/parentPage")
    public String getParentPage(@RequestParam String id) {
        return "listParents";
    }

    @GetMapping("/parentsSchoolStudents")
    public String getParentsSchoolStudents(@RequestParam String id) {
        return "listParentsSchoolStudents";
    }

    @GetMapping("/classPage")
    public String getClassPage(@RequestParam String id) {
        return "classPage";
    }
}
