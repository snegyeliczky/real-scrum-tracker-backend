package com.codecool.scrum.repository;

import com.codecool.scrum.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
    Set<AppUser> findByUsernameContaining(String keyWord);
}
