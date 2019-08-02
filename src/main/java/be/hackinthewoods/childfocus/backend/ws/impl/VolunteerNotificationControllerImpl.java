package be.hackinthewoods.childfocus.backend.ws.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.service.MissionPayLoadConverter;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
import be.hackinthewoods.childfocus.backend.ws.VolunteerNotificationController;
import org.springframework.util.Assert;

import java.util.List;

public class VolunteerNotificationControllerImpl implements VolunteerNotificationController {

    private final VolunteerNotificationService volunteerNotificationService;
    private final BroadcastService broadcastService;

    VolunteerNotificationControllerImpl(VolunteerNotificationService volunteerNotificationService, BroadcastService broadcastService) {
        this.volunteerNotificationService = volunteerNotificationService;
        this.broadcastService = broadcastService;
    }

    @Override
    public void subscribe(List<String> clientTokens) {
        Assert.notNull(clientTokens, "The client tokens mustn't be null");
        broadcastService.subscribe(clientTokens, "missions");
    }

    @Override
    public void send(List<Mission> missions) {
        Assert.notNull(missions, "The missions mustn't be null");
        Assert.isTrue(missions.stream().allMatch(m -> m.getStatus().equals(Mission.Status.PENDING)), "The missions must be pending");
        volunteerNotificationService.saveMissions(missions);
        missions.stream()
          .map(MissionPayLoadConverter::convert)
          .forEach(payLoad -> broadcastService.broadcast(payLoad, "missions"));
    }

    @Override
    public void answer(Mission mission) {
        Assert.notNull(mission, "The mission mustn't be null");
        Assert.isTrue(!mission.getStatus().equals(Mission.Status.PENDING), "The mission must be accepted or refused");
        volunteerNotificationService.answerMission(mission);
    }
}
