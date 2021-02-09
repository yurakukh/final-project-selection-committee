package com.finalproject.committee.controller;

import com.finalproject.committee.entity.Faculty;
import com.finalproject.committee.entity.User;
import com.finalproject.committee.repository.FacultyRepository;
import com.finalproject.committee.repository.SubjectRepository;
import com.finalproject.committee.service.FacultyService;
import com.finalproject.committee.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class FacultyController {

    private final FacultyService facultyService;
    private final UserService userService;
    private final SubjectRepository subjectRepository;
    private final FacultyRepository facultyRepository;

    @ModelAttribute("faculty")
    public Faculty faculty() {
        return new Faculty();
    }


    public FacultyController(FacultyService facultyService,
                             UserService userService,
                             SubjectRepository subjectRepository,
                             FacultyRepository facultyRepository) {
        this.facultyService = facultyService;
        this.userService = userService;
        this.subjectRepository = subjectRepository;
        this.facultyRepository = facultyRepository;
    }


    @GetMapping("/faculty")
    public String listBooks(
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {

        Page<Faculty> facultyPage = facultyService.findPaginated(PageRequest.of(page - 1, size));

        model.addAttribute("facultyPage", facultyPage);

        int totalPages = facultyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "faculties-paginated";
    }


    @GetMapping("/add-new-faculty")
    public String addFaculty(Model model){
        model.addAttribute("subjects", subjectRepository.findAll());

        return "faculty-add";
    }

    @PostMapping("/add-new-faculty")
    public String confirmFacultyAdding(Model model,
                                       @ModelAttribute("faculty") @Valid Faculty faculty,
                                       BindingResult result){

        if (result.hasErrors()) {
            model.addAttribute("subjects", subjectRepository.findAll());
            return "faculty-add";
        }

        Faculty existing = facultyService.findByFaculty(faculty.getFaculty());
        if (existing != null) {
            result.rejectValue("faculty", "", "Такий факультет вже зареєстрований");
        }

        facultyService.saveFaculty(faculty);
        return "redirect:/faculty";
    }

    @PostMapping("/faculty/{faculty}/remove")
    public String deleteFaculty(@PathVariable(value = "faculty") String faculty, Model model){

        facultyService.removeFaculty(faculty);

        return "redirect:/faculty";
    }


    @PostMapping("/faculty/{faculty}/apply")
    public String applyToFaculty(@PathVariable(value = "faculty") String faculty,
                                 Principal currentUser,
                                 Model model){


        userService.addFacultyToUser(currentUser.getName(), faculty);

        return "redirect:/faculty";
    }


}
