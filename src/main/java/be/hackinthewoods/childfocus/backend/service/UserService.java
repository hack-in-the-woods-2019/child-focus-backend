package be.hackinthewoods.childfocus.backend.service;

import be.hackinthewoods.childfocus.backend.entity.WebUser;

import java.util.Optional;

public interface UserService {

    Optional<String> login(String username, String password);

    Optional<WebUser> findByToken(String token);
}
