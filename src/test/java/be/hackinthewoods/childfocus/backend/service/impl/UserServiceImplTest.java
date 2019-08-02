package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import be.hackinthewoods.childfocus.backend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Before
    public void beforeEach() {
        service = new UserServiceImpl(userRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void login_nullUsername() {
        service.login(null, "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void login_nullPassword() {
        service.login("user", null);
    }

    @Test
    public void login_userExists() {
        String username = "username";
        String password = "password";
        WebUser user = new WebUser(username, password);
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        String token = service.login(username, password);

        assertThat(token).isNotBlank();
        assertThat(user).extracting(WebUser::getToken).isEqualTo(token);
    }

    @Test
    public void login_userDontExist() {
        String username = "username";
        String password = "password";
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        String token = service.login(username, password);

        assertThat(token).isEmpty();
    }
}