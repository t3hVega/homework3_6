package com.homework.homework36.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.model.Avatar;
import com.homework.homework36.model.Student;
import com.homework.homework36.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private StudentService studentService;

    @Test
    public void shouldCorrectlyGetStudentCount() throws Exception{
        when(studentService.count()).thenReturn(5);

        Integer expected = 5;

        ResponseEntity<Integer> response = testRestTemplate.getForEntity("/student/count", Integer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldCorrectlyGetStudentAvgAge() throws Exception{
        when(studentService.avgAge()).thenReturn(12.0f);

        Float expected = 12.0f;

        ResponseEntity<Float> response = testRestTemplate.getForEntity("/student/avg-age", Float.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldCorrectlyGetLastStudents() throws Exception{
        List<Student> students = Arrays.asList(
                new Student(1L, "Harry", 11),
                new Student(2L, "Ron", 11),
                new Student(3L, "Fred", 13),
                new Student(4L, "George", 13),
                new Student(5L, "Percy", 15)
        );
        when(studentService.lastStudents()).thenReturn(students);

        String expected = objectMapper.writeValueAsString(students);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/student/last-students", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

}