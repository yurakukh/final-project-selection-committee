package com.finalproject.committee.service;


import com.finalproject.committee.config.WebSecurityConfig;
import com.finalproject.committee.dto.UserDTO;
import com.finalproject.committee.entity.Faculty;
import com.finalproject.committee.entity.Role;
import com.finalproject.committee.entity.User;
import com.finalproject.committee.repository.FacultyRepository;
import com.finalproject.committee.repository.UserRepository;
import com.finalproject.committee.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public UserService(UserRepository userRepository, FacultyRepository facultyRepository) {
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong email");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }


    public User save(UserDTO registration) {

        User user = UserMapper.userFromDTO(registration);

        return userRepository.save(user);
    }

    @Transactional
    public User addFacultyToUser(String user, String faculty) {

        User userFromDb = findByEmail(user);
        Faculty facultyFromDb = facultyRepository.findByFaculty(faculty);

        userFromDb.addFaculty(facultyFromDb);

        return userRepository.save(userFromDb);
    }

    @Transactional
    public User assignUserToFaculty(String email, String faculty) {
        User user = userRepository.findByEmail(email);
        user.removeAllFaculties();

        user.addFaculty(facultyRepository.findByFaculty(faculty));
        user.accept();

        return userRepository.save(user);
    }
}
