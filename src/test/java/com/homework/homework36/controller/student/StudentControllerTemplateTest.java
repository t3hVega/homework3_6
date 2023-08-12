package com.homework.homework36.controller.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.model.Student;
import com.homework.homework36.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private StudentRepository studentRepository;


    @AfterEach
    public void clearDB() {
        studentRepository.deleteAll();
    }

    @Test
    public void shouldCorrectlyGetStudentInfo() {
        Long studentId = persistTestStudent("Harry", 11).getId();

        ResponseEntity<Student> response = testRestTemplate.getForEntity("/student/{id}", Student.class, studentId);

        Student student = response.getBody();
        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getId()).isNotNull();

    }

    @Test
    public void shouldCorrectlyCreateStudent() {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);

        ResponseEntity<Student> response = testRestTemplate.postForEntity("/student", student, Student.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isNotNull();
        Assertions.assertThat(response.getBody().getAge()).isNotNull();

    }

    @Test
    public void shouldCorrectlyDeleteStudent() {
        Long studentId = persistTestStudent("Harry", 11).getId();

        testRestTemplate.delete("/student/{id}", studentId);

        Assertions.assertThat(studentRepository.findById(studentId)).isEmpty();
    }

    private Student persistTestStudent(String name, int age) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return studentRepository.save(student);
    }
}