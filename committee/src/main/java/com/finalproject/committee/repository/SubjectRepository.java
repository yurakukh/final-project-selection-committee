package com.finalproject.committee.repository;

import com.finalproject.committee.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findByName(String subjectName);
}
