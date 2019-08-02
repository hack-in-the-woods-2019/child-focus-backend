package be.hackinthewoods.childfocus.backend.controller.impl;

import be.hackinthewoods.childfocus.backend.controller.VolunteerNotificationController;
import be.hackinthewoods.childfocus.backend.entity.Mission;
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
public class VolunteerNotificationControllerImplTest {

    private VolunteerNotificationController controller;

    @Mock
    private VolunteerNotificationService volunteerNotificationService;
    @Mock
    private BroadcastService broadcastService;

    @Before
    public void beforeEach() {
        controller = new VolunteerNotificationControllerImpl(
          volunteerNotificationService,
          broadcastService
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void subscribe_nullClientTokens() {
        controller.subscribe(null);
    }

    @Test
    public void subscribe() {
        List<String> clientTokens = Arrays.asList("token1", "token2");

        controller.subscribe(clientTokens);

        verify(broadcastService).subscribe(clientTokens, "missions");
    }

    @Test(expected = IllegalArgumentException.class)
    public void send_nullMissions() {
        controller.send(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void send_nonPendingMissions() {
        Mission mission1 = new Mission();
        mission1.setStatus(PENDING);
        Mission mission2 = new Mission();
        mission2.setStatus(ACCEPTED);

        controller.send(null);
    }

    @Test
    public void send() {
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setStatus(PENDING);
        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setStatus(PENDING);

        controller.send(Arrays.asList(mission1, mission2));

        verify(volunteerNotificationService).saveMissions(Arrays.asList(mission1, mission2));
        verify(broadcastService).broadcast(Map.of(
          "id", "1",
          "status", "PENDING"
        ), topic());
        verify(broadcastService).broadcast(Map.of(
          "id", "2",
          "status", "PENDING"
        ), topic());
    }

    private String topic() {
        return "missions";
    }

    @Test(expected = IllegalArgumentException.class)
    public void answer_nullMission() {
        controller.answer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void answer_pendingMission() {
        Mission mission = new Mission();
        mission.setStatus(PENDING);

        controller.answer(mission);
    }

    @Test
    public void answer() {
        Mission mission = new Mission();
        mission.setStatus(ACCEPTED);

        controller.answer(mission);

        verify(volunteerNotificationService).answerMission(mission);
    }
}