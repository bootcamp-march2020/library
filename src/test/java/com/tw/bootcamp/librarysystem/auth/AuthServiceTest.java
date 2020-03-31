package com.tw.bootcamp.librarysystem.auth;

import com.tw.bootcamp.librarysystem.auth.service.AuthService;
import com.tw.bootcamp.librarysystem.book.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    AuthService authService;

    @BeforeEach
    private void setUp() {
        authService = new AuthService();
    }

    @Test
    public void whenSignedInShouldReturnAuthenticatedUserDetails() {
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        OAuth2AuthenticationToken mockOAuthToken = getMockPricipal();
        when(auth.getPrincipal()).thenReturn(mockOAuthToken);
        when(securityContext.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(securityContext);
        User expectedUser = new User("Kumar", "test@gm.com");
        User user = authService.getOAuthUserDetails();

        assertEquals(user.getEmail(), expectedUser.getEmail());
        assertEquals(user.getName(), expectedUser.getName());
    }

    private OAuth2AuthenticationToken getMockPricipal() {
        OAuth2User oAuth2User = mock(OAuth2User.class);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", "Kumar");
        attributes.put("email", "test@gm.com");
        when(oAuth2User.getAttributes()).thenReturn(attributes);
        OAuth2AuthenticationToken mockOAuthToken = new OAuth2AuthenticationToken(oAuth2User, null, "authorizedClientRegistrationId");
        return mockOAuthToken;
    }
}
