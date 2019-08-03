package be.hackinthewoods.childfocus.backend;

import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class LoginScenarii extends AbstractEndToEndTest {

    @Test
    public void login_wrongPassword() throws Exception {
        try {
            login(user.getUsername(), "wrongPassword");
            fail("Login should've gone wrong because password doesn't match");
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(IllegalAccessException.class);
        }
    }

    @Test
    public void login_match() throws Exception {
        String token = login(user.getUsername(), user.getPassword());

        assertThat(token).isNotBlank();
    }
}
