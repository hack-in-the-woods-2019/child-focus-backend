package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.PosterController;
import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.service.PosterService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class PosterControllerImpl implements PosterController {

    private final PosterService posterService;

    PosterControllerImpl(PosterService posterService) {
        this.posterService = posterService;
    }

    @Override
    @PostMapping(path = "/api/posters")
    public void update(@RequestBody Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");

        posterService.update(poster);
    }
}
