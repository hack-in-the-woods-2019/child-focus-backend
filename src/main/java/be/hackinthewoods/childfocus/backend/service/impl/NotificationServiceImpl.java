package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.MissionRepository;
import be.hackinthewoods.childfocus.backend.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final MissionRepository missionRepository;

    NotificationServiceImpl(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public void saveMissions(List<Mission> missions) {
        Assert.notNull(missions, "The missions mustn't be null");
        Assert.isTrue(missions.stream().allMatch(m -> m.getStatus().equals(Mission.Status.PENDING)), "The missions must be pending");

        missionRepository.saveAll(missions);
    }

    @Override
    public void answerMission(Mission mission) {
        Assert.notNull(mission, "The mission mustn't be null");
        Assert.isTrue(!mission.getStatus().equals(Mission.Status.PENDING), "The mission must be accepted or refused");

        missionRepository.findById(mission.getId())
          .ifPresentOrElse(
            m -> {
                m.setStatus(mission.getStatus());
                missionRepository.save(m);
            },
            () -> { throw new IllegalStateException("Missing mission"); }
        );
    }

    @Override
    public List<Mission> pendingMissions(WebUser user) {
        Assert.notNull(user, "The user mustn't be null");

        return missionRepository.findByWebUserAndStatus(user, Mission.Status.PENDING);
    }
}
