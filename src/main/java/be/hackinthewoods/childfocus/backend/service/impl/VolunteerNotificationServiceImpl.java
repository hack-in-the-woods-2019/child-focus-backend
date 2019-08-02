package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.repository.MissionRepository;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.service.MissionPayLoadConverter;
import be.hackinthewoods.childfocus.backend.service.VolunteerNotificationService;
import org.springframework.util.Assert;

import java.util.List;

public class VolunteerNotificationServiceImpl implements VolunteerNotificationService {

    private final MissionRepository missionRepository;
    private final BroadcastService broadcastService;

    VolunteerNotificationServiceImpl(MissionRepository missionRepository, BroadcastService broadcastService) {
        this.missionRepository = missionRepository;
        this.broadcastService = broadcastService;
    }

    @Override
    public void sendMissions(List<Mission> missions) {
        Assert.notNull(missions, "The missions mustn't be null");
        Assert.isTrue(missions.stream().allMatch(m -> m.getStatus().equals(Mission.Status.PENDING)), "The missions must be pending");
        missionRepository.saveAll(missions);
        missions.stream()
          .map(MissionPayLoadConverter::convert)
          .forEach(payLoad -> broadcastService.broadcast(payLoad, null));
    }

    @Override
    public void answerMission(Mission mission) {
        Assert.notNull(mission, "The mission mustn't be null");
        Assert.isTrue(!mission.getStatus().equals(Mission.Status.PENDING), "The mission must be accepted or refused");
        missionRepository.save(mission);
    }
}
