package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.controller.api.AccessController;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccessControllerImplTest {

    private AccessController controller;

    @Mock
    private UserService userService;

    @Before
    public void beforeEach() {
        controller = new AccessControllerImpl(userService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void login_usernameNull() throws Exception {
        controller.getToken(null, "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void login_passwordNull() throws Exception {
        controller.getToken("username", null);
    }

    @Test(expected = IllegalAccessException.class)
    public void login_emptyToken() throws Exception {
        when(userService.login("username", "password")).thenReturn(Optional.empty());

        controller.getToken("username", "password");
    }

    @Test
    public void login() throws Exception {
        Optional<String> expectedToken = Optional.of("token");
        when(userService.login("username", "password")).thenReturn(expectedToken);

        String token = controller.getToken("username", "password");

        assertThat(token).isEqualTo(expectedToken.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUser_nullToken() throws Exception {
        controller.getUser(null);
    }

    @Test(expected = IllegalAccessException.class)
    public void getUser_tokenDoesntExist() throws Exception {
        String token = "token";
        when(userService.findByToken(token)).thenReturn(Optional.empty());

        controller.getUser(token);
    }

    @Test
    public void getUser() throws Exception {
        String token = "token";
        WebUser expectedUser = new WebUser("username", "password");
        when(userService.findByToken(token)).thenReturn(Optional.of(expectedUser));

        WebUser user = controller.getUser(token);

        assertThat(user).isEqualTo(expectedUser);
    }
}