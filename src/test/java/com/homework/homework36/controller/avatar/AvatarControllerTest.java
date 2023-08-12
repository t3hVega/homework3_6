package com.homework.homework36.controller.avatar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework36.dto.AvatarDTO;
import com.homework.homework36.model.Avatar;
import com.homework.homework36.service.AvatarService;
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
public class AvatarControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private AvatarService avatarService;

    @Test
    public void shouldCorrectlyGetAvatars() throws Exception{
        List<AvatarDTO> avatars = Arrays.asList();

        int pageNum = 1;
        int pageSize = 2;

        when(avatarService.getAvatars(1, 2)).thenReturn(avatars);

        String expected = objectMapper.writeValueAsString(avatars);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/avatar/avatars?pageNum=1&pageSize=2", String.class, pageNum, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

}