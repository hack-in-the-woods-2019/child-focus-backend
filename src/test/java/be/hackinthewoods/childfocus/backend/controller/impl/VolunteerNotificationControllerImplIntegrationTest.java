package be.hackinthewoods.childfocus.backend.controller.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
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

import java.util.Arrays;

import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.ACCEPTED;
import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.PENDING;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VolunteerNotificationControllerImpl.class)
public class VolunteerNotificationControllerImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerNotificationService volunteerNotificationService;
    @MockBean
    private BroadcastService broadcastService;

    @Test
    @WithAnonymousUser
    public void subscribe() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Arrays.asList("token1", "token2"));

        mockMvc.perform(post("/api/missions/subscribe")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void send_unauthorized() throws Exception {
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setStatus(PENDING);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setStatus(PENDING);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Arrays.asList(mission1, mission2));

        mockMvc.perform(post("/api/missions/send")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void send() throws Exception {
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setStatus(PENDING);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setStatus(PENDING);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Arrays.asList(mission1, mission2));

        mockMvc.perform(post("/api/missions/send")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void answer_unauthorized() throws Exception {
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setStatus(ACCEPTED);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mission);

        mockMvc.perform(post("/api/missions/answer")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void answer() throws Exception {
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setStatus(ACCEPTED);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mission);

        mockMvc.perform(post("/api/missions/answer")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isOk());
    }
}