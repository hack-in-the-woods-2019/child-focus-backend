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
import static org.mockito.Mockito.when;

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
        when(userRepository.findByEmailAndPassword(username, password)).thenReturn(Optional.of(user));

        Optional<String> token = service.login(username, password);

        assertThat(token).isPresent();
        assertThat(user).extracting(WebUser::getToken).isEqualTo(token.get());
    }

    @Test
    public void login_userDontExist() {
        String username = "username";
        String password = "password";
        when(userRepository.findByEmailAndPassword(username, password)).thenReturn(Optional.empty());

        Optional<String> token = service.login(username, password);

        assertThat(token).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByToken_nullToken() {
        service.findByToken(null);
    }

    @Test
    public void findByToken() {
        String token = "token";
        Optional<WebUser> expectedUser = Optional.of(new WebUser("username", "password"));
        when(userRepository.findByToken(token)).thenReturn(expectedUser);

        Optional<WebUser> actualUser = service.findByToken(token);

        assertThat(actualUser).isEqualTo(expectedUser);
    }
}