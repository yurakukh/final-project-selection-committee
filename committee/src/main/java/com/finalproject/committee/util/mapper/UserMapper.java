package com.finalproject.committee.util.mapper;

import com.finalproject.committee.config.WebSecurityConfig;
import com.finalproject.committee.dto.UserDTO;
import com.finalproject.committee.entity.Role;
import com.finalproject.committee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public class UserMapper {

    public static User userFromDTO(UserDTO userDTO){

        return User.newBuilder()
                .setFirstName(userDTO.getFirstName())
                .setLastName(userDTO.getLastName())
                .setPatronymic(userDTO.getPatronymic())

                .setEmail(userDTO.getEmail())
                .setPassword(WebSecurityConfig.passwordEncoder.encode(userDTO.getPassword()))

                .setRegion(userDTO.getRegion())
                .setCity(userDTO.getCity())
                .setEducationInstitution(userDTO.getEducationInstitution())

                .setFirstSubject(userDTO.getFirstSubject())
                .setSecondSubject(userDTO.getSecondSubject())
                .setThirdSubject(userDTO.getThirdSubject())

                .setFirstMark(userDTO.getFirstMark())
                .setSecondMark(userDTO.getSecondMark())
                .setThirdMark(userDTO.getThirdMark())

                .setCertificateScore(userDTO.getCertificateScore())

                .setAccepted(false)

                .setRoles(Collections.singleton(Role.ROLE_USER))

                .build();
    }

}
