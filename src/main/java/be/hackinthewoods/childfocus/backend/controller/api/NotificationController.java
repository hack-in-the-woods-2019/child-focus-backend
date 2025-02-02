package be.hackinthewoods.childfocus.backend.controller.api;

import be.hackinthewoods.childfocus.backend.entity.Mission;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface NotificationController {

    /**
     * Subscribes clients to mission notifications
     * @throws IllegalArgumentException when {@code clientTokens} is {@code null} or empty
     */
    void subscribe(List<String> clientTokens);

    /**
     * Sends missions to volunteers
     * @throws IllegalArgumentException when {@code missions} is {@code null}
     * @throws IllegalArgumentException when any {@code mission} is not pending
     */
    void send(List<Mission> missions);

    /**
     * Gives new missions to volunteers
     * @param httpServletRequest
     */
    List<Mission> poll(HttpServletRequest httpServletRequest);

    /**
     * Treats the answer of a volunteer for missing person
     * @throws IllegalArgumentException when {@code missingPerson} is {@code null}
     * @throws IllegalArgumentException when {@code mission} is pending
     */
    void answer(Mission mission);
}
