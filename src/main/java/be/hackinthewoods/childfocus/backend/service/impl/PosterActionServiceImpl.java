package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.PosterRepository;
import be.hackinthewoods.childfocus.backend.service.PosterActionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PosterActionServiceImpl implements PosterActionService {

    private final PosterRepository posterRepository;

    PosterActionServiceImpl(PosterRepository posterRepository) {
        this.posterRepository = posterRepository;
    }

    @Override
    public void putPoster(Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");
        posterRepository.save(poster);
    }

    @Override
    public void removePoster(Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");
        posterRepository.delete(poster);
    }
}
