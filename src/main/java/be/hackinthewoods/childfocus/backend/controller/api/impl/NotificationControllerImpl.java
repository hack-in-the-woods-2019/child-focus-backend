package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.NotificationController;
import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import be.hackinthewoods.childfocus.backend.service.UserService;
import be.hackinthewoods.childfocus.backend.utils.MissionPayLoadConverter;
import be.hackinthewoods.childfocus.backend.service.NotificationService;
import com.sun.net.httpserver.HttpsParameters;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService notificationService;
    private final BroadcastService broadcastService;
    private final UserService userService;

    NotificationControllerImpl(NotificationService notificationService, BroadcastService broadcastService, UserService userService) {
        this.notificationService = notificationService;
        this.broadcastService = broadcastService;
        this.userService = userService;
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

        notificationService.saveMissions(missions);
        missions.stream()
          .map(MissionPayLoadConverter::convert)
          .forEach(payLoad -> broadcastService.broadcast(payLoad, topic()));
    }

    @Override
    @GetMapping(path = "/api/missions/poll")
    public List<Mission> poll(HttpServletRequest httpServletRequest) {
        return userService.findByToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
          .map(notificationService::newMissionsFor)
          .orElseThrow(() -> new IllegalStateException("No user matches token"));
    }

    @Override
    @PostMapping(path = "/api/missions/answer")
    public void answer(@RequestBody Mission mission) {
        Assert.notNull(mission, "The mission mustn't be null");
        Assert.isTrue(!mission.getStatus().equals(Mission.Status.PENDING), "The mission must be accepted or refused");

        notificationService.answerMission(mission);
    }

    private String topic() {
        return "missions";
    }
}
