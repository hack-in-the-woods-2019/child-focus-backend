package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.NotificationController;
import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.service.NotificationService;
import be.hackinthewoods.childfocus.backend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.*;

import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.ACCEPTED;
import static be.hackinthewoods.childfocus.backend.entity.Mission.Status.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerImplTest {

    private NotificationController controller;

    @Mock
    private NotificationService notificationService;
    @Mock
    private BroadcastService broadcastService;
    @Mock
    private UserService userService;

    @Before
    public void beforeEach() {
        controller = new NotificationControllerImpl(
          notificationService,
          broadcastService,
          userService
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

        verify(notificationService).saveMissions(Arrays.asList(mission1, mission2));
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

    @Test(expected = IllegalStateException.class)
    public void poll_userDoesntExist() {
        when(userService.findByToken("token")).thenReturn(Optional.empty());

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "token");
        controller.poll(httpServletRequest);
    }

    @Test
    public void poll() {
        WebUser user = new WebUser("username", "password");
        when(userService.findByToken("token")).thenReturn(Optional.of(user));

        Mission mission = new Mission();
        mission.setId(1L);
        mission.setStatus(PENDING);
        List<Mission> missions = Collections.singletonList(mission);
        when(notificationService.newMissionsFor(user)).thenReturn(missions);

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "token");
        assertThat(controller.poll(httpServletRequest)).containsExactlyElementsOf(missions);
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

        verify(notificationService).answerMission(mission);
    }
}