package com.finalproject.committee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Please, enter your first name")
    private String firstName;

    @NotBlank(message = "Please, enter your last name")
    private String lastName;

    @NotBlank(message = "Please, enter your patronymic")
    private String patronymic;

    @NotBlank(message = "Email should be filled")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Incorrect email format")
    private String email;

    @NotBlank(message = "Create password")
    @Length(min = 6, message = "Password should be al least 6 characters")
    private String password;


    private String city;

    private String region;

    @NotBlank(message = "Enter your school")
    private String educationInstitution;

    private String firstSubject;
    private String secondSubject;
    private String thirdSubject;

    @NotNull(message = "Enter a mark")
    private Integer firstMark;
    @NotNull(message = "Enter a mark")
    private Integer secondMark;
    @NotNull(message = "Enter a mark")
    private Integer thirdMark;

    @NotNull(message = "Enter your certificate score")
    private Integer certificateScore;

}

