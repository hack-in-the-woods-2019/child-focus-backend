package be.hackinthewoods.childfocus.backend.service;

import be.hackinthewoods.childfocus.backend.entity.Poster;

public interface PosterActionService {

    /**
     * A volunteer puts a poster for a missingPerson at given coordinates
     * @throws IllegalArgumentException when {@code poster} is {@code null}
     */
    void putPoster(Poster poster);

    /**
     * A volunteer removes a poster for a missingPerson from given coordinates
     * @throws IllegalArgumentException when {@code poster} is {@code null}
     */
    void removePoster(Poster poster);
}
