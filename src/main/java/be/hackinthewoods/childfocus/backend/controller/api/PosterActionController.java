package be.hackinthewoods.childfocus.backend.controller.api;

import be.hackinthewoods.childfocus.backend.controller.api.model.PosterAction;

public interface PosterActionController {

    /**
     * Allows to put or remove a poster for a missingPerson at given coordinates
     */
    void action(PosterAction action);
}
