package com.homework.homework36.controller.faculty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.repository.FacultyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerMockRepositoryTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private FacultyRepository facultyRepository;


    @AfterEach
    public void clearDB() {
        facultyRepository.deleteAll();
    }

    @Test
    public void shouldCorrectlyEditFaculty() throws Exception{
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(faculty), headers);

        ResponseEntity<String> response = testRestTemplate.exchange("/faculty", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(objectMapper.writeValueAsString(faculty), response.getBody(), false);
    }

    private Faculty persistTestFaculty(String name, String color) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return facultyRepository.save(faculty);
    }
}