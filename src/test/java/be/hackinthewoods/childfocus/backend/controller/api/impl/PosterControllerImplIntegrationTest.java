package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.service.PosterService;
import be.hackinthewoods.childfocus.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PosterControllerImplIntegrationTest {

    private static final String TOKEN = "token";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private PosterService posterService;

    @Before
    public void beforeEach() {
        when(userService.findByToken(TOKEN)).thenReturn(Optional.of(new WebUser("username", "password")));
    }

    @Test
    @WithAnonymousUser
    public void action_unauthorized() throws Exception {
        Poster poster = new Poster();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(poster);

        mockMvc.perform(post("/api/posters")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void action() throws Exception {
        Poster poster = new Poster();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(poster);

        mockMvc.perform(post("/api/posters")
          .header(HttpHeaders.AUTHORIZATION, TOKEN)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isOk());
    }
}