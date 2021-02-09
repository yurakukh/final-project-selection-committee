package com.finalproject.committee.controller;

import com.finalproject.committee.service.FacultyService;
import com.finalproject.committee.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApplicationsController {
    private final FacultyService facultyService;
    private final UserService userService;

    public ApplicationsController(FacultyService facultyService, UserService userService) {
        this.facultyService = facultyService;
        this.userService = userService;
    }

    @GetMapping("/faculty/{faculty}/applications")
    public String getFacultyApplications(@PathVariable(value = "faculty") String faculty, Model model){

        model.addAttribute("faculty", facultyService.findByFaculty(faculty));

        return "faculty-applicants";
    }

    @GetMapping("/faculty/{faculty}/applications/{email}/confirm")
    public String getFacultyApplications(@PathVariable(value = "faculty") String faculty,
                                         @PathVariable(value = "email") String email,
                                         Model model){


        userService.assignUserToFaculty(email, faculty);

        model.addAttribute("faculty", facultyService.findByFaculty(faculty));

        return "redirect:/faculty/{faculty}/applications";
    }


}
