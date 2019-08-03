package be.hackinthewoods.childfocus.backend.controller.api;

import be.hackinthewoods.childfocus.backend.entity.Poster;

public interface PosterController {

    /**
     * Allows to put or remove a poster for a missingPerson at given coordinates
     * @throws IllegalArgumentException when {@code poster} is {@code null}
     */
    void update(Poster poster);
}
