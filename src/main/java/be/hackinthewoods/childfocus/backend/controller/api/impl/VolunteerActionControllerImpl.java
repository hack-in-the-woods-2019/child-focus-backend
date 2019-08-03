package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.VolunteerActionController;
import be.hackinthewoods.childfocus.backend.controller.api.model.VolunteerAction;
import be.hackinthewoods.childfocus.backend.service.VolunteerActionService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VolunteerActionControllerImpl implements VolunteerActionController {

    private final VolunteerActionService service;

    VolunteerActionControllerImpl(VolunteerActionService service) {
        this.service = service;
    }

    @Override
    @PostMapping(path = "/api/actions")
    public void action(@RequestBody VolunteerAction action) {
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
