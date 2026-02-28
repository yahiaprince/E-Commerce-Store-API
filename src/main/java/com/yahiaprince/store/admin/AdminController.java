package com.yahiaprince.store.admin;

import com.yahiaprince.store.setup.ChangeUserRoleRequest;
import com.yahiaprince.store.users.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Tag(name = "Admin")
public class AdminController {

    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<?> changeUserRole(
            @PathVariable Long id,
            @Valid @RequestBody ChangeUserRoleRequest request) {
        var userOpt = userRepository.findById(id);
        if (userOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var user = userOpt.get();
        user.setRole(request.role());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello Admin!";
    }
}