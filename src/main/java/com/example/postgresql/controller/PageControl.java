package com.example.postgresql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageControl {
    @GetMapping("/profileSchoolStudent")
    public String getProfileSchoolStudent(@RequestParam("id") String id) {
        return "profileSchoolStudent";
    }

    @GetMapping("/profileTeacher")
    public String getProfileTeacher(@RequestParam("id") String id) {
        return "profileTeacher";
    }

    @GetMapping("/profileAdministrator")
    public String getProfileAdministrator(@RequestParam("id") String id) {
        return "profileAdministrator";
    }

    @GetMapping("/parentPage")
    public String getParentPage(@RequestParam("id") String id) {
        return "listParents";
    }

    @GetMapping("/classPage")
    public String getClassPage(@RequestParam("id") String id) {
        return "classPage";
    }


}
