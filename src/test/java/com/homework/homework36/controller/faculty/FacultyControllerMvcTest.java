package com.homework.homework36.controller.faculty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.controller.FacultyController;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.model.Student;
import com.homework.homework36.service.FacultyService;
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

@WebMvcTest(FacultyController.class)
public class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;

    @Test
    public void getFacultyInfo() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");

        when(facultyService.findFaculty(1L)).thenReturn(faculty);

        ResultActions resultActions = mockMvc.perform(get("/faculty/1")
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

    @Test
    public void shouldCorrectlyCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        when(facultyService.addFaculty(faculty)).thenReturn(faculty);

        ResultActions resultActions = mockMvc.perform(post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty))
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    public void shouldCorrectlyEditFaculty() throws Exception{
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");

        when(facultyService.editFaculty(faculty)).thenReturn(faculty);

        ResultActions resultActions = mockMvc.perform(put("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty))
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }

    @Test
    public void shouldCorrectlyDeleteFaculty() throws Exception{
        Long facultyId = 1L;

        ResultActions resultActions = mockMvc.perform(delete("/faculty/" + facultyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldCorrectlyFindFaculties() throws Exception{
        Faculty faculty1 = new Faculty(1L, "Gryffindor", "Red");
        Faculty faculty2 = new Faculty(2L, "Гриффиндор", "Red");

        when(facultyService.findByColor("Red")).thenReturn(Arrays.asList(faculty1, faculty2));

        ResultActions resultActions = mockMvc.perform(get("/faculty/by-color?color=Red")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(faculty1, faculty2))));
    }

    @Test
    public void shouldCorrectlyFindStudentsByFacultyId() throws Exception{
        Student student1 = new Student(1L, "Harry", 11);
        Student student2 = new Student(2L, "Ron", 11);

        Long facultyId = 1L;

        when(facultyService.findStudentsByFacultyId(facultyId)).thenReturn(Arrays.asList(student1, student2));

        ResultActions resultActions = mockMvc.perform(get("/faculty/1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student1, student2))));
    }
}