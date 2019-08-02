package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.repository.MissionRepository;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.ACCEPTED;
import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.PENDING;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerNotificationServiceImplTest {

    private VolunteerNotificationService service;

    @Mock
    private MissionRepository missionRepository;

    @Before
    public void beforeEach() {
        service = new VolunteerNotificationServiceImpl(missionRepository);
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