package com.homework.homework36.controller.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.model.Student;
import com.homework.homework36.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerMockServiceTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private StudentService studentService;

    @Test
    public void shouldCorrectlyGetFacultyFromStudentId() {
        Student student = new Student(1L, "Harry", 11);
        Long studentId = student.getId();

        Faculty faculty1 = new Faculty(1L, "Gryffindor", "Red");

        when(studentService.findFacultyByStudentId(1L)).thenReturn(faculty1);

        ResponseEntity<Faculty> response = testRestTemplate.getForEntity("/student/{id}/faculty", Faculty.class, studentId);

        Faculty faculty2 = response.getBody();

        org.assertj.core.api.Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        org.assertj.core.api.Assertions.assertThat(response.getBody()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getBody().getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getBody().getName()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getBody().getColor()).isNotNull();
    }




}