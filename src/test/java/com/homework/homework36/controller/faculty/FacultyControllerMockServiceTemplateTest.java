package com.homework.homework36.controller.faculty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.model.Student;
import com.homework.homework36.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerMockServiceTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private FacultyService facultyService;

    @Test
    public void shouldCorrectlyFindFaculties() throws Exception{
        List<Faculty> faculties = Arrays.asList(
                new Faculty(1L, "Gryffindor", "Red"),
                new Faculty(2L, "Гриффиндор", "Red")
        );

        when(facultyService.findByColor("Red")).thenReturn(faculties);

        String expected = objectMapper.writeValueAsString(faculties);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/faculty/by-color?color=Red", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void shouldCorrectlyFindStudentsByFacultyId() throws Exception{
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");

        Long facultyId = faculty.getId();

        List<Student> students = Arrays.asList(
                new Student(1L, "Harry", 11),
                new Student(2L, "Ron", 11)
        );

        when(facultyService.findStudentsByFacultyId(facultyId)).thenReturn(students);

        String expected = objectMapper.writeValueAsString(students);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/faculty/{id}/students", String.class, facultyId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}