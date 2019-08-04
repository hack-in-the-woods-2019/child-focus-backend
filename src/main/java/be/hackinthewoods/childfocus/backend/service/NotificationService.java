package be.hackinthewoods.childfocus.backend.service;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;

import java.util.List;

public interface NotificationService {

    /**
     * Sends missions to volunteers
     * @throws IllegalArgumentException when {@code missions} is {@code null}
     * @throws IllegalArgumentException when any {@code mission} is not pending
     */
    void saveMissions(List<Mission> missions);

    /**
     * The user answers a mission
     * @throws IllegalArgumentException when {@code mission} is {@code null}
     * @throws IllegalArgumentException when {@code mission} is pending
     */
    void answerMission(Mission mission);

    /**
     * Gives the new missions for a user
     * @throws IllegalArgumentException when {@code user} is {@code null}
     */
    List<Mission> newMissionsFor(WebUser user);
}
