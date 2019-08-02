package be.hackinthewoods.childfocus.backend.controller;

import be.hackinthewoods.childfocus.backend.controller.model.VolunteerAction;

public interface VolunteerActionController {

    /**
     * Allows to put or remove a poster for a missingPerson at given coordinates
     */
    void action(VolunteerAction action);
}
