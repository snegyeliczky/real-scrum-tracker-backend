package com.codecool.scrum.controller;

import com.codecool.scrum.model.credentials.UserCredentials;
import com.codecool.scrum.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/registration")
    public void registration(@RequestBody UserCredentials newUser){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        authService.registration(newUser);
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody UserCredentials user, HttpServletResponse response){
        return authService.signIn(user,response);
    }


}
