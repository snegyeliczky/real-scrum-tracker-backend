package com.codecool.scrum.service;

import com.codecool.scrum.model.AppUser;
import com.codecool.scrum.model.credentials.UserCredentials;
import com.codecool.scrum.repository.AppUserRepository;
import com.codecool.scrum.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }

    @Autowired
    private AppUserRepository appUserRepository;

    public void registration(UserCredentials newUser) {
        AppUser appUser = AppUser.builder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .roles(Arrays.asList("ADMIN"))
                .build();
        appUserRepository.saveAndFlush(appUser);
    }



}
