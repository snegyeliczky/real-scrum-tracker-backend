package com.codecool.scrum.util;

import com.codecool.scrum.model.AppUser;
import com.codecool.scrum.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Util {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        return appUserRepository.findByUsername(username).get();
    }
}
