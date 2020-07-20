package com.codecool.scrum.controller;

import com.codecool.scrum.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(){
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
