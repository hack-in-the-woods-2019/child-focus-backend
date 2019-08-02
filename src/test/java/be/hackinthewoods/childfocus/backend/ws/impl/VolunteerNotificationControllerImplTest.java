package be.hackinthewoods.childfocus.backend.ws.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
import be.hackinthewoods.childfocus.backend.ws.VolunteerNotificationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.ACCEPTED;
import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.PENDING;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerNotificationControllerImplTest {

    private VolunteerNotificationController controller;

    @Mock
    private VolunteerNotificationService service;

    @Before
    public void beforeEach() {
        controller = new VolunteerNotificationControllerImpl(service);
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
        mission1.setStatus(PENDING);
        Mission mission2 = new Mission();
        mission2.setStatus(PENDING);

        controller.send(Arrays.asList(mission1, mission2));

        verify(service).sendMissions(Arrays.asList(mission1, mission2));
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

        verify(service).answerMission(mission);
    }
}