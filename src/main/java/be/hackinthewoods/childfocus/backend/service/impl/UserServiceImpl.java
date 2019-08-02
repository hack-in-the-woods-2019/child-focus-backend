package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import be.hackinthewoods.childfocus.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String login(String username, String password) {
        Assert.hasText(username, "The username mustn't be blank");
        Assert.hasText(password, "The password mustn't be blank");

        Optional<WebUser> user = userRepository.findByUsername(username);
        user.ifPresent(u -> u.setToken(UUID.randomUUID().toString()));
        return user.map(WebUser::getToken).orElse("");
    }
}
