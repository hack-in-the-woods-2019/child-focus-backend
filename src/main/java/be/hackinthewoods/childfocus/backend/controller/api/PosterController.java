package be.hackinthewoods.childfocus.backend.controller.api;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PosterController {

    /**
     * Allows to put or remove a poster for a missingPerson at given coordinates
     * @throws IllegalArgumentException when {@code poster} is {@code null}
     */
    void update(Poster poster);

    List<Poster> posters();

    @GetMapping(path = "/api/posters")
    List<Poster> userPosters(HttpServletRequest httpServletRequest);
}
