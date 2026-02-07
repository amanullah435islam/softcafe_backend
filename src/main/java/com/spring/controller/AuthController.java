package com.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.spring.config.JwtTokenProvider;
import com.spring.model.User;
import com.spring.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    
  
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        try {
            // 1️⃣ Username uniqueness check
            if (userService.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Username already exists"
                ));
            }

            // 2️⃣ Create user entity
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword())); // Password encode
            user.setName(request.getName());        
            user.setEmail(request.getEmail());

            // 3️⃣ Save user
            User savedUser = userService.save(user);

            // 4️⃣ Authenticate the new user to generate token
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // 5️⃣ Generate JWT token
            String jwtToken = jwtTokenProvider.generateToken(authentication);

            // 6️⃣ Build response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("token", jwtToken);
            response.put("user", Map.of(
                    "id", savedUser.getId(),
                    "username", savedUser.getUsername(),
                    "name", savedUser.getName(),
                    "email", savedUser.getEmail()
            ));

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Invalid credentials"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Registration failed",
                    "details", e.getMessage()
            ));
        }
    }

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            String token = jwtTokenProvider.generateToken(auth);
            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
    
    
    
    

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "User logged out successfully. Please discard token on client side.");
//        return ResponseEntity.ok(response);
//    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // JWT stateless → server-side কিছু invalidate করার দরকার নেই
        return ResponseEntity.ok(Map.of(
                "message", "Logout successful. Please remove token from client."
        ));
    }


    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();

        // Remove password before sending
        List<Map<String, Object>> userList = users.stream().map(u -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", u.getId());
            map.put("username", u.getUsername());
            map.put("name", u.getName());
            map.put("email", u.getEmail());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(userList);

    }
}

