package com.codecool.scrum.service;

import com.codecool.scrum.model.AppUser;
import com.codecool.scrum.model.credentials.UserCredentials;
import com.codecool.scrum.repository.AppUserRepository;
import com.codecool.scrum.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    public ResponseEntity signIn(UserCredentials user, HttpServletResponse response) {
        try {
            String username = user.getUsername();
            // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(username, roles);

            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            /*
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roles);
            model.put("token", token);
             */
            return ResponseEntity.ok(user);

        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password");
        }
    }
}
