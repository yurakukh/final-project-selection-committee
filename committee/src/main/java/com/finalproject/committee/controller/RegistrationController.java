package com.finalproject.committee.controller;


import com.finalproject.committee.dto.UserDTO;
import com.finalproject.committee.entity.User;
import com.finalproject.committee.repository.CityRepository;
import com.finalproject.committee.repository.RegionRepository;
import com.finalproject.committee.repository.SubjectRepository;
import com.finalproject.committee.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@Controller
public class RegistrationController {

	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

	private final UserService userService;

	private final RegionRepository regionRepository;

	private final CityRepository cityRepository;

	private final SubjectRepository subjectRepository;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder){
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@Autowired
	public RegistrationController(UserService userService,
								  RegionRepository regionRepository,
								  CityRepository cityRepository,
								  SubjectRepository subjectRepository) {
		this.userService = userService;
		this.regionRepository = regionRepository;
		this.cityRepository = cityRepository;
		this.subjectRepository = subjectRepository;
	}

	@ModelAttribute("user")
	public UserDTO userRegistrationDto() {
		return new UserDTO();
	}


	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("regions", regionRepository.findAll());
		model.addAttribute("cities", cityRepository.findAll());
		model.addAttribute("subjects", subjectRepository.findAll());
		return "registration";
	}


	@PostMapping("/register")
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto,
									  BindingResult bindingResult,
									  Model model) {

		log.info(">> userDTO : {}", userDto.toString());

		User existing = userService.findByEmail(userDto.getEmail());

		if (existing != null) {
			bindingResult.rejectValue("email", "", "Account with this e-mail already registered");
		}

		if(bindingResult.hasErrors()){
			model.addAttribute("regions", regionRepository.findAll());
			model.addAttribute("cities", cityRepository.findAll());
			model.addAttribute("subjects", subjectRepository.findAll());
			return "registration";
		}


		userService.save(userDto);
		return "redirect:/login";
	}

}
