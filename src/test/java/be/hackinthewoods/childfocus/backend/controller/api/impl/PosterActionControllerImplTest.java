package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.PosterActionController;
import be.hackinthewoods.childfocus.backend.controller.api.model.PosterAction;
import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.service.PosterActionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PosterActionControllerImplTest {

    private PosterActionController controller;

    @Mock
    private PosterActionService service;

    @Before
    public void beforeEach() {
        controller = new PosterActionControllerImpl(service);
    }

    @Test(expected = IllegalArgumentException.class)
    public void action_nullVolunteerAction() {
        controller.action(null);
    }

    @Test
    public void action_put() {
        Poster poster = new Poster();

        controller.action(PosterAction.put(poster));

        verify(service).putPoster(poster);
    }

    @Test
    public void action_remove() {
        Poster poster = new Poster();

        controller.action(PosterAction.remove(poster));

        verify(service).removePoster(poster);
    }
}