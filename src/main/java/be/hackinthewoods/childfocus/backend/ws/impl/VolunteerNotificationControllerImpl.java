package be.hackinthewoods.childfocus.backend.ws.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
import be.hackinthewoods.childfocus.backend.ws.VolunteerNotificationController;
import org.springframework.util.Assert;

import java.util.List;

public class VolunteerNotificationControllerImpl implements VolunteerNotificationController {

    private final VolunteerNotificationService service;

    VolunteerNotificationControllerImpl(VolunteerNotificationService service) {
        this.service = service;
    }

    @Override
    public void send(List<Mission> missions) {
        Assert.notNull(missions, "The missions mustn't be null");
        Assert.isTrue(missions.stream().allMatch(m -> m.getStatus().equals(Mission.Status.PENDING)), "The missions must be pending");
        service.sendMissions(missions);
    }

    @Override
    public void answer(Mission mission) {
        Assert.notNull(mission, "The mission mustn't be null");
        Assert.isTrue(!mission.getStatus().equals(Mission.Status.PENDING), "The mission must be accepted or refused");
        service.answerMission(mission);
    }
}
