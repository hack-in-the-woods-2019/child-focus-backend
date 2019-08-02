package be.hackinthewoods.childfocus.backend.controller.impl;

import be.hackinthewoods.childfocus.backend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccessControllerImpl.class)
public class AccessControllerImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void login() throws Exception {
        String username = "username";
        String password = "password";
        String token = "token";
        when(userService.login(username, password)).thenReturn(token);

        MockHttpServletResponse response = mockMvc.perform(post("/api/login")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .param("username", username)
          .param("password", password)
        )
          .andExpect(status().isOk())
          .andReturn().getResponse();

        assertThat(response.getContentAsString()).isEqualTo(token);
    }
}