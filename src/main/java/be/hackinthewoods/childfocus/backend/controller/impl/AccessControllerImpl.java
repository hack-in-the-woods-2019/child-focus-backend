package be.hackinthewoods.childfocus.backend.controller.impl;

import be.hackinthewoods.childfocus.backend.controller.AccessController;
import be.hackinthewoods.childfocus.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessControllerImpl implements AccessController {

    private final UserService userService;

    AccessControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/api/login")
    public String login(String username, String password) {
        Assert.hasText(username, "The username musn't be blank");
        Assert.hasText(password, "The password musn't be blank");

        return userService.login(username, password);
    }
}
