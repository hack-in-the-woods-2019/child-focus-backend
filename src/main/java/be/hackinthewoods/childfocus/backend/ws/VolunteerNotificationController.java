package be.hackinthewoods.childfocus.backend.ws;

import be.hackinthewoods.childfocus.backend.entity.Mission;

import java.util.List;

public interface VolunteerNotificationController {

    /**
     * Sends missions to volunteers
     * @throws IllegalArgumentException when {@code missions} is {@code null}
     * @throws IllegalArgumentException when any {@code mission} is not pending
     */
    void send(List<Mission> missions);

    /**
     * Treats the answer of a volunteer for missing person
     * @throws IllegalArgumentException when {@code missingPerson} is {@code null}
     * @throws IllegalArgumentException when {@code mission} is pending
     */
    void answer(Mission mission);
}
