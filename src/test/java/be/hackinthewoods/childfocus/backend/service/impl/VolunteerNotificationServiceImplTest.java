package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.repository.MissionRepository;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

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
    public void sendMissions_nullMissions() {
        service.sendMissions(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendMissions_nonPendingMissions() {
        Mission mission1 = new Mission();
        mission1.setStatus(PENDING);
        Mission mission2 = new Mission();
        mission2.setStatus(ACCEPTED);

        service.sendMissions(Arrays.asList(mission1, mission2));
    }

    @Test
    public void sendMissions() {
        Mission mission1 = new Mission();
        mission1.setStatus(PENDING);
        Mission mission2 = new Mission();
        mission2.setStatus(PENDING);
        List<Mission> missions = Arrays.asList(mission1, mission2);

        service.sendMissions(missions);

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