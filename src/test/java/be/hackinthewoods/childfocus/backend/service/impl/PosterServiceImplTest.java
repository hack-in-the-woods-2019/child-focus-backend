package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.PosterRepository;
import be.hackinthewoods.childfocus.backend.service.PosterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PosterServiceImplTest {

    private PosterService posterService;

    @Mock
    private PosterRepository posterRepository;

    @Before
    public void beforeEach() {
        posterService = new PosterServiceImpl(posterRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_nullPoster() {
        posterService.update(null);
    }

    @Test
    public void update() {
        Poster poster = new Poster();
        posterService.update(poster);
        verify(posterRepository).save(poster);
    }
}