package be.hackinthewoods.childfocus.backend;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.MissionRepository;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
public class MissionScenarii {

    private ObjectMapper mapper;

    private WebUser user;
    @Autowired
    private UserRepository userRepository;

    private Mission mission;
    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    @Transactional
    public void beforeEach() {
        mapper = new ObjectMapper();

        initUser();

        mission = createMission(null, Mission.Status.PENDING);
        missionRepository.save(mission);
    }

    private void initUser() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userRepository.findByUsername("user").ifPresentOrElse(
          u -> user = u,
          () -> {
              user = new WebUser("user", passwordEncoder.encode("password"));
              userRepository.save(user);
          }
        );
    }

    @Test
    public void acceptMission() throws Exception {
        String token = login();

        Mission answeredMission = createMission(mission.getId(), Mission.Status.ACCEPTED);
        answerMission(answeredMission, token);
        verifySavedMission(answeredMission);
    }

    @Test
    public void refuseMission() throws Exception {
        String token = login();

        Mission answeredMission = createMission(mission.getId(), Mission.Status.REFUSED);
        answerMission(answeredMission, token);
        verifySavedMission(answeredMission);
    }

    private String login() throws Exception {
        return mockMvc.perform(post("/token")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .param("username", user.getUsername())
          .param("password", user.getPassword())
        )
          .andExpect(status().isOk())
          .andReturn().getResponse().getContentAsString();
    }

    private Mission createMission(Long id, Mission.Status status) {
        Mission result = new Mission();
        result.setId(id);
        result.setStatus(status);
        return result;
    }

    private void answerMission(Mission mission, String token) throws Exception {
        String missionJson = mapper.writeValueAsString(mission);
        mockMvc.perform(post("/api/missions/answer")
          .header(HttpHeaders.AUTHORIZATION, token)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(missionJson)
        )
          .andExpect(status().isOk());
    }

    private void verifySavedMission(Mission mission) {
        Optional<Mission> savedMission = missionRepository.findById(mission.getId());
        assertThat(savedMission)
          .isPresent()
          .map(Mission::getStatus)
          .contains(mission.getStatus());
    }
}
