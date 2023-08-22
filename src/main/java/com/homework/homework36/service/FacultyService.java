package com.homework.homework36.service;

import com.homework.homework36.model.Student;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Добавляем факультет - {}", faculty.getName());
        return facultyRepository.save(faculty);
    }
    public Faculty findFaculty(long id) {
        logger.info("Ищем факультет по его id - {}", id);
        return facultyRepository.findById(id).get();
    }
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Редактируем факультет - {}", faculty.getName());
        return facultyRepository.save(faculty);
    }
    public void deleteFaculty(long id) {
        logger.info("Удаляем факультет по его id - {}", id);
        facultyRepository.deleteById(id);
    }
    public List<Faculty> findByColor(String color) {
        logger.info("Ищем факультеты по его цвету - {}", color);
        return facultyRepository.findByColorIgnoreCase(color);
    }
    public List<Student> findStudentsByFacultyId(long id) {
        logger.info("Ищем студентов факультета по его id - {}", id);
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElse(null);
    }

    public List<Faculty> getFacultiesByName(String name) {
        logger.info("Ищем факультеты по его имени - {}", name);
        return facultyRepository.getFacultiesByName(name);
    };

    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Ищем факультеты по его цвету - {}", color);
        return facultyRepository.getFacultiesByColor(color);
    };

    public String longestName() {
        logger.info("Ищем факультет с самым длинным названием");
        List<Faculty> facultyList = facultyRepository.findAll();
        return facultyList
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).get();
    }

    public int calculate() {
        int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        return sum;
    }

}