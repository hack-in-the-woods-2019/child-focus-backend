package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.PosterController;
import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.PosterRepository;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import be.hackinthewoods.childfocus.backend.service.PosterService;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Transactional
public class PosterControllerImpl implements PosterController {

    private final PosterService posterService;
    private final PosterRepository posterRepository;
    private final UserRepository userRepository;

    PosterControllerImpl(PosterService posterService, PosterRepository posterRepository, UserRepository userRepository) {
        this.posterService = posterService;
        this.posterRepository = posterRepository;
        this.userRepository = userRepository;
    }

    @Override
    @PostMapping(path = "/api/posters")
    public void update(@RequestBody Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");

        posterService.update(poster);
    }

    @Override
    @GetMapping(path = "/api/posters")
    public List<Poster> posters() {
        return posterRepository.findAll();
    }

    @Override
    @GetMapping(path = "/api/posters/byuser")
    public List<Poster> userPosters(HttpServletRequest httpServletRequest) {
        return userRepository.findByToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
          .map(posterRepository::findAllByUser)
          .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
