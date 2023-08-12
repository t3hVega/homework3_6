package com.homework.homework36.controller.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.model.Student;
import com.homework.homework36.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerMockRepositoryTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private StudentRepository studentRepository;

    @AfterEach
    public void clearDB() {
        studentRepository.deleteAll();
    }

    @Test
    public void shouldCorrectlyEditStudent() throws Exception{
        Student student = new Student(1L, "Harry", 11);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(student), headers);

        ResponseEntity<String> response = testRestTemplate.exchange("/student", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(objectMapper.writeValueAsString(student), response.getBody(), false);
    }


    @Test
    public void shouldCorrectlyFindStudents() throws Exception{
        List<Student> students = Arrays.asList(
                new Student(1L, "Harry", 11),
                new Student(2L, "Ron", 11)
        );

        when(studentRepository.findByAge(11)).thenReturn(students);

        String expected = objectMapper.writeValueAsString(students);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/student/by-age?age=11", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void shouldCorrectlyFindStudentsBetween() throws Exception{
        List<Student> students = Arrays.asList(
                new Student(1L, "Harry", 11),
                new Student(2L, "Ron", 11)
        );

        when(studentRepository.findByAgeBetween(10, 13)).thenReturn(students);

        String expected = objectMapper.writeValueAsString(students);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/student/by-age-between?min=10&max=13", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}