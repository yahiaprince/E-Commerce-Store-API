package com.yahiaprince.store.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort) {
        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userMapper.toUserDto(user.get()));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("email", "Email is already in use"));
        }

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        var userDto = userMapper.toUserDto(user);
        var uri = uriBuilder.path("/users/{id}").build(user.getId());
        return ResponseEntity.created(uri).body(userDto);
    }
    
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        userMapper.update(request, user.get());
        userRepository.save(user.get());

        return ResponseEntity.ok(userMapper.toUserDto(user.get()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        userRepository.delete(user.get());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        var user = userRepository.findById(id);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        if (!passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user.get());
        return ResponseEntity.noContent().build();
    }

}
