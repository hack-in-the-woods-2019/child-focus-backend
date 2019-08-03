package be.hackinthewoods.childfocus.backend.controller.api.impl;

import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccessControllerImplIntegrationTest {

    private static final String TOKEN = "token";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Before
    public void beforeEach() {
        when(userService.findByToken(TOKEN)).thenReturn(Optional.of(new WebUser("username", "password")));
    }

    @Test
    public void getToken() throws Exception {
        String username = "username";
        String password = "password";
        Optional<String> token = Optional.of("token");
        when(userService.login(username, password)).thenReturn(token);

        MockHttpServletResponse response = mockMvc.perform(post("/token")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .param("username", username)
          .param("password", password)
        )
          .andExpect(status().isOk())
          .andReturn().getResponse();

        assertThat(response.getContentAsString()).isEqualTo(token.get());
    }

    @Test
    @WithAnonymousUser
    public void getUser_unauthorized() throws Exception {
        String token = "token";
        mockMvc.perform(post("/api/users/" + token)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void getUser() throws Exception {
        WebUser user = new WebUser("username", "password");
        when(userService.findByToken(TOKEN)).thenReturn(Optional.of(user));

        MockHttpServletResponse response = mockMvc.perform(get("/api/users/" + TOKEN)
          .header(HttpHeaders.AUTHORIZATION, TOKEN)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
          .andExpect(status().isOk())
          .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        assertThat(response.getContentAsString()).isEqualTo(json);
    }
}