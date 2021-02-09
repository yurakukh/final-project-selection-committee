package com.finalproject.committee.service;

import com.finalproject.committee.entity.Faculty;
import com.finalproject.committee.entity.User;
import com.finalproject.committee.repository.FacultyRepository;
import com.finalproject.committee.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final UserRepository userRepository;

    public FacultyService(FacultyRepository facultyRepository, UserRepository userRepository) {
        this.facultyRepository = facultyRepository;
        this.userRepository = userRepository;
    }

    public Faculty saveFaculty(Faculty faculty){
        Faculty facultyToAdd = new Faculty();
        facultyToAdd.setFaculty(faculty.getFaculty());
        facultyToAdd.setSubject1(faculty.getSubject1());
        facultyToAdd.setSubject2(faculty.getSubject2());
        facultyToAdd.setSubject3(faculty.getSubject3());
        facultyToAdd.setBudgetPassingScore(faculty.getBudgetPassingScore());
        facultyToAdd.setContractPassingScore(faculty.getContractPassingScore());
        facultyToAdd.setBudgetPlaces(faculty.getBudgetPlaces());
        facultyToAdd.setContractPlaces(faculty.getContractPlaces());

        return facultyRepository.save(facultyToAdd);
    }

    public Collection<Faculty> findAll(){
        return facultyRepository.findAll();
    }

    public Faculty findByFaculty(String faculty){
        return facultyRepository.findByFaculty(faculty);
    }


    public void removeFaculty(String facultyName){

        Faculty existingFaculty = facultyRepository.findByFaculty(facultyName);

        for(User user : existingFaculty.getUsers()){
            user.getFaculties().remove(existingFaculty);
            if(user.getAccepted())
                user.disableAcception();
        }

        facultyRepository.delete(existingFaculty);
    }

    public Page<Faculty> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Faculty> allFaculties = facultyRepository.findAll();
        List<Faculty> list;

        if (allFaculties.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allFaculties.size());
            list = allFaculties.subList(startItem, toIndex);
        }

        Page<Faculty> facultyPage
                = new PageImpl<Faculty>(list, PageRequest.of(currentPage, pageSize), allFaculties.size());

        return facultyPage;
    }

}