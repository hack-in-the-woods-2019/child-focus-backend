package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.PosterRepository;
import be.hackinthewoods.childfocus.backend.service.PosterService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PosterServiceImpl implements PosterService {

    private final PosterRepository posterRepository;

    PosterServiceImpl(PosterRepository posterRepository) {
        this.posterRepository = posterRepository;
    }

    @Override
    public void update(Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");
        posterRepository.save(poster);
    }
}
