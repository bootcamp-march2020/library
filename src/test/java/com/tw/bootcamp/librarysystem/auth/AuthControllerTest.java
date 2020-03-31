package com.tw.bootcamp.librarysystem.auth;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private final static String ID_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";


    private void setUp() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "vijay@gmail.com");
        claims.put("name", "test");
        claims.put("sub", "114056670896003368498");
        OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(),
                Instant.now().plusSeconds(60), claims);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken(idToken));
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
    public void whenAuthenticatedShouldGiveCurrentUserDetail() throws Exception {
        setUp();
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/auth/currentuser"))
                .andExpect(jsonPath("email", is("vijay@gmail.com")))
                .andExpect(jsonPath("name", is("test")));
    }

    private OAuth2AuthenticationToken authenticationToken(OidcIdToken idToken) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        OidcUser user = new DefaultOidcUser(authorities, idToken);
        return new OAuth2AuthenticationToken(user, authorities, "oidc");
    }

}
