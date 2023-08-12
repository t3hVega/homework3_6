package com.homework.homework36.controller.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.controller.StudentController;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.model.Student;
import com.homework.homework36.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Test
    public void shouldCorrectlyGetStudentInfo() throws Exception {
        Student student = new Student(1L, "Harry", 11);

        when(studentService.findStudent(1L)).thenReturn(student);

        ResultActions resultActions = mockMvc.perform(get("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    public void shouldCorrectlyCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);

        when(studentService.addStudent(student)).thenReturn(student);

        ResultActions resultActions = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student))
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldCorrectlyEditStudent() throws Exception {
        Student student = new Student(1L, "Harry", 11);

        when(studentService.editStudent(student)).thenReturn(student);

        ResultActions resultActions = mockMvc.perform(put("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student))
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }

    @Test
    public void shouldCorrectlyDeleteStudent() throws Exception {
        Long studentId = 1L;

        ResultActions resultActions = mockMvc.perform(delete("/student/" + studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void findStudents() throws Exception{
        Student student1 = new Student(1L, "Harry", 11);
        Student student2 = new Student(2L, "Ron", 11);

        when(studentService.findByAge(11)).thenReturn(Arrays.asList(student1, student2));

        ResultActions resultActions = mockMvc.perform(get("/student/by-age?age=11")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student1, student2))));
    }

    @Test
    public void findStudentsBetween() throws Exception{
        Student student1 = new Student(1L, "Harry", 11);
        Student student2 = new Student(2L, "Ron", 11);

        when(studentService.findByAgeBetween(10, 13)).thenReturn(Arrays.asList(student1, student2));

        ResultActions resultActions = mockMvc.perform(get("/student/by-age-between?min=10&max=13")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student1, student2))));
    }

    @Test
    public void findFacultyByStudentId() throws Exception{
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");

        when(studentService.findFacultyByStudentId(1L)).thenReturn(faculty);

        ResultActions resultActions = mockMvc.perform(get("/student/1/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }
}