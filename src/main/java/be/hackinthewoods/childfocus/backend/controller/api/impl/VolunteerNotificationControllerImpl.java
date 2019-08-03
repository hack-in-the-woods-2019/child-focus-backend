package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.VolunteerNotificationController;
import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.utils.MissionPayLoadConverter;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class VolunteerNotificationControllerImpl implements VolunteerNotificationController {

    private final VolunteerNotificationService volunteerNotificationService;
    private final BroadcastService broadcastService;

    VolunteerNotificationControllerImpl(VolunteerNotificationService volunteerNotificationService, BroadcastService broadcastService) {
        this.volunteerNotificationService = volunteerNotificationService;
        this.broadcastService = broadcastService;
    }

    @Override
    @PostMapping(path = "/api/missions/subscribe")
    @Transactional(readOnly = true)
    public void subscribe(@RequestBody List<String> clientTokens) {
        Assert.notNull(clientTokens, "The client tokens mustn't be null");

        broadcastService.subscribe(clientTokens, topic());
    }

    @Override
    @PostMapping(path = "/api/missions/send")
    public void send(@RequestBody List<Mission> missions) {
        Assert.notNull(missions, "The missions mustn't be null");
        Assert.isTrue(missions.stream().allMatch(m -> m.getStatus().equals(Mission.Status.PENDING)), "The missions must be pending");

        volunteerNotificationService.saveMissions(missions);
        missions.stream()
          .map(MissionPayLoadConverter::convert)
          .forEach(payLoad -> broadcastService.broadcast(payLoad, topic()));
    }

    @Override
    @PostMapping(path = "/api/missions/answer")
    public void answer(@RequestBody Mission mission) {
        Assert.notNull(mission, "The mission mustn't be null");
        Assert.isTrue(!mission.getStatus().equals(Mission.Status.PENDING), "The mission must be accepted or refused");

        volunteerNotificationService.answerMission(mission);
    }

    private String topic() {
        return "missions";
    }
}
