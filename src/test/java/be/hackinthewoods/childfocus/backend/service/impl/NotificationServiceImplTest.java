package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.MissionRepository;
import be.hackinthewoods.childfocus.backend.service.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.ACCEPTED;
import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    private NotificationService service;

    @Mock
    private MissionRepository missionRepository;

    @Before
    public void beforeEach() {
        service = new NotificationServiceImpl(missionRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveMissions_nullMissions() {
        service.saveMissions(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveMissions_nonPendingMissions() {
        Mission mission1 = new Mission();
        mission1.setStatus(PENDING);
        Mission mission2 = new Mission();
        mission2.setStatus(ACCEPTED);

        service.saveMissions(Arrays.asList(mission1, mission2));
    }

    @Test
    public void saveMissions() {
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setStatus(PENDING);
        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setStatus(PENDING);
        List<Mission> missions = Arrays.asList(mission1, mission2);

        service.saveMissions(missions);

        verify(missionRepository).saveAll(missions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newMissionsFor_nullUser() {
        service.newMissionsFor(null);
    }

    @Test
    public void newMissionsFor() {
        WebUser user = new WebUser("username", "password");
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setStatus(PENDING);
        List<Mission> missions = Collections.singletonList(mission);
        when(missionRepository.findByWebUser(user)).thenReturn(missions);

        assertThat(service.newMissionsFor(user)).containsExactlyElementsOf(missions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void answer_nullMission() {
        service.answerMission(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void answer_pendingMission() {
        Mission mission = new Mission();
        mission.setStatus(PENDING);

        service.answerMission(mission);
    }

    @Test
    public void answer() {
        Mission mission = new Mission();
        mission.setStatus(ACCEPTED);

        service.answerMission(mission);

        verify(missionRepository).save(mission);
    }
}