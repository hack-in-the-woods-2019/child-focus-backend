package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.PosterRepository;
import be.hackinthewoods.childfocus.backend.service.VolunteerActionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerActionServiceImplTest {

    private VolunteerActionService volunteerActionService;

    @Mock
    private PosterRepository posterRepository;

    @Before
    public void beforeEach() {
        volunteerActionService = new VolunteerActionServiceImpl(posterRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putPoster_nullPoster() {
        volunteerActionService.putPoster(null);
    }

    @Test
    public void putPoster() {
        Poster poster = new Poster();
        volunteerActionService.putPoster(poster);
        verify(posterRepository).save(poster);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removePoster_nullPoster() {
        volunteerActionService.removePoster(null);
    }

    @Test
    public void removePoster() {
        Poster poster = new Poster();
        volunteerActionService.removePoster(poster);
        verify(posterRepository).delete(poster);
    }
}