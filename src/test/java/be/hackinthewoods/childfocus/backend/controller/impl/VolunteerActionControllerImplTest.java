package be.hackinthewoods.childfocus.backend.controller.impl;

import be.hackinthewoods.childfocus.backend.controller.VolunteerActionController;
import be.hackinthewoods.childfocus.backend.controller.model.VolunteerAction;
import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.service.VolunteerActionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerActionControllerImplTest {

    private VolunteerActionController controller;

    @Mock
    private VolunteerActionService service;

    @Before
    public void beforeEach() {
        controller = new VolunteerActionControllerImpl(service);
    }

    @Test(expected = IllegalArgumentException.class)
    public void action_nullVolunteerAction() {
        controller.action(null);
    }

    @Test
    public void action_put() {
        Poster poster = new Poster();

        controller.action(VolunteerAction.put(poster));

        verify(service).putPoster(poster);
    }

    @Test
    public void action_remove() {
        Poster poster = new Poster();

        controller.action(VolunteerAction.remove(poster));

        verify(service).removePoster(poster);
    }
}