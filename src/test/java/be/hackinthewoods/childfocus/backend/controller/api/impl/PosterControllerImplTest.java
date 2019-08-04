package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.PosterController;
import be.hackinthewoods.childfocus.backend.entity.DisplayLocation;
import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import be.hackinthewoods.childfocus.backend.service.PosterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PosterControllerImplTest {

    private PosterController controller;

    @Mock
    private PosterService service;

    @Mock
    private UserRepository userRepository;

    @Before
    public void beforeEach() {
        controller = new PosterControllerImpl(service, null, userRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void action_nullVolunteerAction() {
        controller.update(null);
    }

    @Test
    public void action() {
        Poster poster = new Poster();
        poster.addDisplayLocation(new DisplayLocation());

        controller.update(poster);

        verify(service).update(poster);
    }
}