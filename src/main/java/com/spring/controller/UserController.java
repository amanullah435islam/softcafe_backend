package com.spring.controller;



import com.spring.dto.UserRequestDto;
import com.spring.dto.UserResponseDto;
import com.spring.model.User;
import com.spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

 // ✅ CREATE USER (CRUD)
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDto dto) {

        if (userService.existsByUsername(dto.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Username already exists"));
        }

        try {
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setEnabled(true);

            User saved = userService.createUser(user);

            return ResponseEntity.ok(
                new UserResponseDto(
                    saved.getId(),
                    saved.getUsername(),
                    saved.getName(),
                    saved.getEmail()
                )
            );

        } catch (RuntimeException ex) {
            if ("USERNAME_EXISTS".equals(ex.getMessage())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Username already exists"));
            }
            throw ex; // other errors
        }
    }

    
    // ✅ GET ALL
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAllUsers().stream()
            .map(u -> new UserResponseDto(
                u.getId(), u.getUsername(), u.getName(), u.getEmail()
            ))
            .collect(Collectors.toList());
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDto dto) {

        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());

        User updated = userService.updateUser(id, u);
       // ResponseEntity.ok(updated);
        return ResponseEntity.ok(
        	    new UserResponseDto(
        	        updated.getId(),
        	        updated.getUsername(),
        	        updated.getName(),
        	        updated.getEmail()
        	    )
        	);

    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}

