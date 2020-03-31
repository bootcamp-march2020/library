package com.tw.bootcamp.librarysystem.auth.service;

import com.tw.bootcamp.librarysystem.book.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    public User getOAuthUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication.getPrincipal();
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        User oAuthUser = new User((String) attributes.get("name"), (String) attributes.get("email"));
        return oAuthUser;
    }
}
