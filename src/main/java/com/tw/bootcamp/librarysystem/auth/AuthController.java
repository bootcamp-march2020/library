package com.tw.bootcamp.librarysystem.auth;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/currentuser")
    public Map<String, Object> getCurrentUser(Principal principal) {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
        return token.getPrincipal().getAttributes();
    }
}
