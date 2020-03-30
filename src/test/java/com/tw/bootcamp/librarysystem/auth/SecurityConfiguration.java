package com.tw.bootcamp.librarysystem.auth;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@TestConfiguration
public class SecurityConfiguration {

    private static final String CLIENT_ID = "clioentid";
    private static final String CLIENT_SECRET = "clientsecret";
    private static final String TOKEN_URL = "http://localhost:8080/login/oauth/access_token";
    private static final String AUTH_URL = "http://localhost:8080/signin/oauth/";
    private static final String REDIRECT_URL = "http://localhost:8080/login/oauth2/cod";

    private final ClientRegistration clientRegistration;

    public SecurityConfiguration() {
        this.clientRegistration = clientRegistration().build();
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    private ClientRegistration.Builder clientRegistration() {
        return ClientRegistration.withRegistrationId("oidc")
                .redirectUriTemplate(REDIRECT_URL)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri(AUTH_URL)
                .tokenUri(TOKEN_URL)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET);
    }

}

