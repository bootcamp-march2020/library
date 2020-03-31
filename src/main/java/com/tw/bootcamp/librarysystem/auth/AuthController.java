package com.tw.bootcamp.librarysystem.auth;

import com.tw.bootcamp.librarysystem.auth.service.AuthService;
import com.tw.bootcamp.librarysystem.book.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/currentuser")
    public User getCurrentUser() {
        return authService.getOAuthUserDetails();
    }
}
