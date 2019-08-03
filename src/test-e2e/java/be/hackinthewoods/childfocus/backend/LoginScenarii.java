package be.hackinthewoods.childfocus.backend;

import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
public class LoginScenarii {

    private WebUser user;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    @Transactional
    public void beforeEach() {
        initUser();
    }

    private void initUser() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userRepository.findByUsername("user").ifPresentOrElse(
          u -> user = u,
          () -> {
              user = new WebUser("user", passwordEncoder.encode("password"));
              userRepository.save(user);
          }
        );
    }

    @Test
    public void login_wrongPassword() throws Exception {
        try {
            login(user.getUsername(), "wrongPassword");
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(IllegalAccessException.class);
        }
    }

    @Test
    public void login() throws Exception {
        String token = login(user.getUsername(), user.getPassword());

        assertThat(token).isNotBlank();
    }

    private String login(String username, String password) throws Exception {
        return mockMvc.perform(post("/token")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .param("username", username)
          .param("password", password)
        )
          .andExpect(status().isOk())
          .andReturn().getResponse().getContentAsString();
    }
}
