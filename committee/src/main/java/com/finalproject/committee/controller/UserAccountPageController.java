package com.finalproject.committee.controller;

import com.finalproject.committee.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserAccountPageController {

    private final UserRepository userRepository;

    public UserAccountPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userAccountPage(Model model){

        return "account";
    }

}
