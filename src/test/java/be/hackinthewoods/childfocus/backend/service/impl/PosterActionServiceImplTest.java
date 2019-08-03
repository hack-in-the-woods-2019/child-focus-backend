package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.PosterRepository;
import be.hackinthewoods.childfocus.backend.service.PosterActionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PosterActionServiceImplTest {

    private PosterActionService posterActionService;

    @Mock
    private PosterRepository posterRepository;

    @Before
    public void beforeEach() {
        posterActionService = new PosterActionServiceImpl(posterRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putPoster_nullPoster() {
        posterActionService.putPoster(null);
    }

    @Test
    public void putPoster() {
        Poster poster = new Poster();
        posterActionService.putPoster(poster);
        verify(posterRepository).save(poster);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removePoster_nullPoster() {
        posterActionService.removePoster(null);
    }

    @Test
    public void removePoster() {
        Poster poster = new Poster();
        posterActionService.removePoster(poster);
        verify(posterRepository).delete(poster);
    }
}