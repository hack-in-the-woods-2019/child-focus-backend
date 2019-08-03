package be.hackinthewoods.childfocus.backend;

import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
public abstract class AbstractEndToEndTest {

    protected ObjectMapper mapper;

    protected WebUser user;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Before
    @Transactional
    public void beforeEach() {
        mapper = new ObjectMapper();

        initUser();
    }

    protected void initUser() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userRepository.findByEmail("user").ifPresentOrElse(
          u -> user = u,
          () -> {
              user = new WebUser("user", passwordEncoder.encode("password"));
              userRepository.save(user);
          }
        );
    }

    protected String login() throws Exception {
        return login(user.getUsername(), user.getPassword());
    }

    protected String login(String username, String password) throws Exception {
        return mockMvc.perform(post("/token")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .param("username", username)
          .param("password", password)
        )
          .andExpect(status().isOk())
          .andReturn().getResponse().getContentAsString();
    }
}
