package com.yahiaprince.store.setup;

import com.yahiaprince.store.users.Role;
import com.yahiaprince.store.users.User;
import com.yahiaprince.store.users.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/setup")
@RequiredArgsConstructor
@Tag(name = "Admin Setup")
public class SetupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${setup.token:}")
    private String setupToken;

    @PostMapping("/admin")
    public ResponseEntity<?> createFirstAdmin(
            @RequestHeader(value = "X-Setup-Token", required = false) String token,
            @Valid @RequestBody SetupAdminRequest req) {

        if (setupToken == null || setupToken.isBlank() || token == null || !setupToken.equals(token)) {
            return ResponseEntity.notFound().build();
        }

        if (userRepository.existsByRole(Role.ADMIN)) {
            return ResponseEntity.notFound().build();
        }

        if (userRepository.existsByEmail(req.email())) {
            return ResponseEntity.badRequest().body(Map.of("email", "Email is already in use"));
        }

        User admin = new User();
        admin.setName(req.name());
        admin.setEmail(req.email());
        admin.setPassword(passwordEncoder.encode(req.password()));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        return ResponseEntity.status(201).body(Map.of("message", "Admin created"));
    }
}