package com.homework.homework36.controller.faculty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.repository.FacultyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FacultyRepository facultyRepository;


    @AfterEach
    public void clearDB() {
        facultyRepository.deleteAll();
    }

    @Test
    public void shouldCorrectlyGetFacultyInfo() {
        Long facultyId = persistTestFaculty("Gryffindor", "Red").getId();

        ResponseEntity<Faculty> response = testRestTemplate.getForEntity("/faculty/{id}", Faculty.class, facultyId);

        Faculty faculty = response.getBody();
        Assertions.assertThat(faculty).isNotNull();
        Assertions.assertThat(faculty.getId()).isNotNull();
    }

    @Test
    public void shouldCorrectlyCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        ResponseEntity<Faculty> response = testRestTemplate.postForEntity("/faculty", faculty, Faculty.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isNotNull();
        Assertions.assertThat(response.getBody().getColor()).isNotNull();
    }

    @Test
    public void shouldCorrectlyDeleteFaculty() {
        Long facultyId = persistTestFaculty("Gryffindor", "Red").getId();

        testRestTemplate.delete("/faculty/{id}", facultyId);

        Assertions.assertThat(facultyRepository.findById(facultyId)).isEmpty();
    }

    private Faculty persistTestFaculty(String name, String color) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return facultyRepository.save(faculty);
    }
}