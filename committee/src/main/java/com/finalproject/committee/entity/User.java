package com.finalproject.committee.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;

    @NotBlank(message = "create password")
    private String password;

    private String city;
    private String region;
    private String educationInstitution;

    private String firstSubject;
    private String secondSubject;
    private String thirdSubject;

    private Integer firstMark;
    private Integer secondMark;
    private Integer thirdMark;

    private Integer certificateScore;

    private Boolean accepted;

    //use one-to-many relation
    @ManyToMany
    @JoinTable(
            name = "user_faculty",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id"))
    private Set<Faculty> faculties = new HashSet<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;


    public void addFaculty(Faculty faculty){
        this.faculties.add(faculty);
    }

    public void removeAllFaculties(){
        this.faculties.clear();
    }

    public void accept(){
        this.accepted = true;
    }

    public void disableAcception(){
        this.accepted = false;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    private User() {
    }


    public static UserBuilder newBuilder() {
        return new User().new UserBuilder();
    }

    public class UserBuilder {

        private UserBuilder(){ }

        public UserBuilder setId(Long id){
            User.this.id = id;
            return this;
        }

        public UserBuilder setFirstName(String firstName){
            User.this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName){
            User.this.lastName = lastName;
            return this;
        }

        public UserBuilder setPatronymic(String patronymic){
            User.this.patronymic = patronymic;
            return this;
        }

        public UserBuilder setEmail(String email){
            User.this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password){
            User.this.password = password;
            return this;
        }
        public UserBuilder setCity(String city){
            User.this.city = city;
            return this;
        }
        public UserBuilder setRegion(String region){
            User.this.region = region;
            return this;
        }
        public UserBuilder setEducationInstitution(String educationInstitution){
            User.this.educationInstitution = educationInstitution;
            return this;
        }
        public UserBuilder setFirstSubject(String firstSubject){
            User.this.firstSubject = firstSubject;
            return this;
        }
        public UserBuilder setSecondSubject(String secondSubject){
            User.this.secondSubject = secondSubject;
            return this;
        }
        public UserBuilder setThirdSubject(String thirdSubject){
            User.this.thirdSubject = thirdSubject;
            return this;
        }
        public UserBuilder setFirstMark(Integer firstMark){
            User.this.firstMark = firstMark;
            return this;
        }
        public UserBuilder setSecondMark(Integer secondMark){
            User.this.secondMark = secondMark;
            return this;
        }
        public UserBuilder setThirdMark(Integer thirdMark){
            User.this.thirdMark = thirdMark;
            return this;
        }
        public UserBuilder setCertificateScore(Integer certificateScore){
            User.this.certificateScore = certificateScore;
            return this;
        }
        public UserBuilder setAccepted(Boolean accepted){
            User.this.accepted = accepted;
            return this;
        }
        public UserBuilder setRoles(Collection<Role> roles){
            User.this.roles = roles;
            return this;
        }
        public User build(){
            return User.this;
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getAccepted();
    }

    @Override
    public String getPassword() {
        return password;
    }

}