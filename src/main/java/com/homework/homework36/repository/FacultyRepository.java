package com.homework.homework36.repository;

import com.homework.homework36.model.Faculty;
import com.homework.homework36.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorIgnoreCase(String color);
    List<Faculty> getFacultiesByName(String name);
    List<Faculty> getFacultiesByColor(String color);
}
