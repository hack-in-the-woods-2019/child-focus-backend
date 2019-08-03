package be.hackinthewoods.childfocus.backend.controller.api;

import be.hackinthewoods.childfocus.backend.entity.WebUser;

public interface AccessController {

    String getToken(String username, String password) throws IllegalAccessException;

    WebUser getUser(String token) throws IllegalAccessException;
}
