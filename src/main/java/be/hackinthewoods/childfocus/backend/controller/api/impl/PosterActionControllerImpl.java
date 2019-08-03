package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.PosterActionController;
import be.hackinthewoods.childfocus.backend.controller.api.model.PosterAction;
import be.hackinthewoods.childfocus.backend.service.PosterActionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class PosterActionControllerImpl implements PosterActionController {

    private final PosterActionService service;

    PosterActionControllerImpl(PosterActionService service) {
        this.service = service;
    }

    @Override
    @PostMapping(path = "/api/actions")
    public void action(@RequestBody PosterAction action) {
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
