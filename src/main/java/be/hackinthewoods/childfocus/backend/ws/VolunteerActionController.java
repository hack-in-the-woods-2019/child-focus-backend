package be.hackinthewoods.childfocus.backend.ws;

import be.hackinthewoods.childfocus.backend.ws.model.VolunteerAction;

public interface VolunteerActionController {

    /**
     * Allows to put or remove a poster for a missingPerson at given coordinates
     */
    void action(VolunteerAction action);
}
