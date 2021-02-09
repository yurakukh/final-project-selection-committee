package com.finalproject.committee.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "faculty_id")
    private Integer id;

    @NotBlank(message = "Enter faculty name")
    private String faculty;

    private String subject1;

    private String subject2;

    private String subject3;

    @Positive
    @Max(value = 400, message = "< 400")
    @NotNull(message = "Enter budget score")
    private Integer budgetPassingScore;

    @Positive
    @Max(value = 400, message = "< 400")
    @NotNull(message = "Enter contract score")
    private Integer contractPassingScore;

    @Positive
    @NotNull(message = "Budget places")
    private Integer budgetPlaces;

    @Positive

    @NotNull(message = "Contract places")
    private Integer contractPlaces;

    @ManyToMany(mappedBy = "faculties")
    private Set<User> users;


}
