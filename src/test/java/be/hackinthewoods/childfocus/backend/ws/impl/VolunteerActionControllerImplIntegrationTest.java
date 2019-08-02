package be.hackinthewoods.childfocus.backend.ws.impl;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.service.VolunteerActionService;
import be.hackinthewoods.childfocus.backend.ws.model.VolunteerAction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VolunteerActionControllerImpl.class)
public class VolunteerActionControllerImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerActionService volunteerActionService;

    @Test
    @WithAnonymousUser
    public void action_unauthorized() throws Exception {
        VolunteerAction action = VolunteerAction.put(new Poster());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(action);

        mockMvc.perform(post("/api/actions")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void action() throws Exception {
        VolunteerAction action = VolunteerAction.put(new Poster());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(action);

        mockMvc.perform(post("/api/actions")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isOk());
    }
}