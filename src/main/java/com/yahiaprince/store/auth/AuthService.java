package com.yahiaprince.store.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yahiaprince.store.users.User;
import com.yahiaprince.store.users.UserRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId).orElse(null);
    }
}