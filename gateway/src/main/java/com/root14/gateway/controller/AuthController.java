package com.root14.gateway.controller;

import com.root14.gateway.model.LoginResponse;
import com.root14.gateway.model.RegisterRequest;
import com.root14.gateway.entity.User;
import com.root14.gateway.model.LoginRequest;
import com.root14.gateway.service.JwtService;
import com.root14.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        final LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtService.generateToken(loginRequest));
        return ResponseEntity.status(200).body(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User user = userService.findByUsername(registerRequest.getUsername());


        if (user != null) {
            if (user.getEmail().equals(registerRequest.getEmail())) {
                return ResponseEntity.status(472).body("Email already exists");
            }
            if (user.getUsername().equals(registerRequest.getUsername())) {
                return ResponseEntity.status(471).body("Username already exists");
            }

        }
        user = User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .enabled(true)
                .build();

        try {
            userService.save(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }

        return ResponseEntity.ok("User registered successfully");
    }


}
