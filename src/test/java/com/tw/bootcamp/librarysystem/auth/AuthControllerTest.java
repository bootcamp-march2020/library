package com.tw.bootcamp.librarysystem.auth;

import com.tw.bootcamp.librarysystem.auth.service.AuthService;
import com.tw.bootcamp.librarysystem.book.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    public void whenUnAuthenticatedShouldThrowUnAuthorizedException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/auth/currentuser"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test")
    public void whenAuthenticatedShouldGiveCurrentUserDetail() throws Exception {
        given(authService.getOAuthUserDetails()).willReturn(new User("test", "vijay@gmail.com"));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/auth/currentuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email", is("vijay@gmail.com")))
                .andExpect(jsonPath("name", is("test")));
    }


}
