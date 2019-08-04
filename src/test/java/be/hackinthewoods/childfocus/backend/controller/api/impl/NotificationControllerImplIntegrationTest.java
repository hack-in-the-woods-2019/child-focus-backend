package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.service.UserService;
import be.hackinthewoods.childfocus.backend.service.NotificationService;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.ACCEPTED;
import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerImplIntegrationTest {

    private static final String TOKEN = "token";

    @Autowired
    private MockMvc mockMvc;

    private WebUser user;
    @MockBean
    private UserService userService;
    @MockBean
    private NotificationService notificationService;
    @MockBean
    private BroadcastService broadcastService;

    @Before
    public void beforeEach() {
        user = new WebUser("username", "password");
        when(userService.findByToken(TOKEN)).thenReturn(Optional.of(user));
    }

    @Test
    @WithAnonymousUser
    public void subscribe_unauthorized() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Arrays.asList("token1", "token2"));

        mockMvc.perform(post("/api/missions/subscribe")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void subscribe() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Arrays.asList("token1", "token2"));

        mockMvc.perform(post("/api/missions/subscribe")
          .header(HttpHeaders.AUTHORIZATION, TOKEN)
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
          .header(HttpHeaders.AUTHORIZATION, TOKEN)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void poll_unauthorized() throws Exception {
        mockMvc.perform(get("/api/missions/poll")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void poll() throws Exception {
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setStatus(PENDING);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setStatus(PENDING);

        when(userService.findByToken(TOKEN)).thenReturn(Optional.of(user));

        List<Mission> missions = Arrays.asList(mission1, mission2);
        when(notificationService.pendingMissions(user)).thenReturn(missions);

        MockHttpServletResponse response = mockMvc.perform(
          get("/api/missions/poll")
            .header(HttpHeaders.AUTHORIZATION, TOKEN)
        )
          .andExpect(status().isOk())
          .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<Mission> actualMissions = mapper.readValue(response.getContentAsString(), new TypeReference<List<Mission>>() {});

        assertThat(actualMissions).containsExactlyElementsOf(missions);
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
          .header(HttpHeaders.AUTHORIZATION, TOKEN)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(json)
        ).andExpect(status().isOk());
    }
}