package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.AccessController;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class AccessControllerImpl implements AccessController {

    private final UserService userService;

    AccessControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/token")
    public String getToken(String username, String password) throws IllegalAccessException {
        Assert.hasText(username, "The username musn't be blank");
        Assert.hasText(password, "The password musn't be blank");

        return userService.login(username, password)
          .orElseThrow(() -> new IllegalAccessException("Username or password doesn't match"));
    }

    @Override
    @GetMapping("/api/users/{token}")
    @Transactional(readOnly = true)
    public WebUser getUser(@PathVariable String token) throws IllegalAccessException {
        Assert.hasText(token, "The token musn't be blank");

        return userService.findByToken(token).orElseThrow(() -> new IllegalAccessException("No user matches this token"));
    }
}
