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

public class MissionScenarii extends AbstractEndToEndTest {

    private Mission mission;
    @Autowired
    private MissionRepository missionRepository;

    public void beforeEach() {
        super.beforeEach();

        mapper = new ObjectMapper();

        mission = createMission(null, Mission.Status.PENDING);
        missionRepository.save(mission);
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
