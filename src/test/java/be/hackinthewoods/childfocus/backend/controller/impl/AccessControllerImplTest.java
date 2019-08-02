package be.hackinthewoods.childfocus.backend.controller.impl;

import be.hackinthewoods.childfocus.backend.controller.AccessController;
import be.hackinthewoods.childfocus.backend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void login_usernameNull() {
        controller.login(null, "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void login_passwordNull() {
        controller.login("username", null);
    }

    @Test
    public void login() {
        String expectedToken = "token";
        when(userService.login("username", "password")).thenReturn(expectedToken);

        String token = controller.login("username", "password");

        assertThat(token).isEqualTo(expectedToken);
    }
}