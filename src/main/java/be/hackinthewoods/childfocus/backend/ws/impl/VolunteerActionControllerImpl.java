package be.hackinthewoods.childfocus.backend.ws.impl;

import be.hackinthewoods.childfocus.backend.service.VolunteerActionService;
import be.hackinthewoods.childfocus.backend.ws.VolunteerActionController;
import be.hackinthewoods.childfocus.backend.ws.model.VolunteerAction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class VolunteerActionControllerImpl implements VolunteerActionController {

    private final VolunteerActionService service;

    VolunteerActionControllerImpl(VolunteerActionService service) {
        this.service = service;
    }

    @Override
    public void action(VolunteerAction action) {
        Assert.notNull(action, "The action mustn't be null");
        switch (action.getType()) {
            case PUT:
                service.putPoster(action.getPoster());
                break;
            case REMOVE:
                service.removePoster(action.getPoster());
                break;
            default:
                throw new IllegalStateException("The action type " + action.getType() + " is not handled");
        }
    }
}
