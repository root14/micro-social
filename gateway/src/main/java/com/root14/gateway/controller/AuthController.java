package com.root14.gateway.controller;

import com.root14.gateway.entity.User;
import com.root14.gateway.model.AuthenticationRequest;
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
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest) {
        final String jwt = jwtService.generateToken(authenticationRequest);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest authenticationRequest) {
        User user = userService.findByUsername(authenticationRequest.getUsername());
        if (user != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user = User.builder()
                .username(authenticationRequest.getUsername())
                .password(authenticationRequest.getPassword())
                .email("emil@gmail.com")
                .enabled(true)
                .build();

        userService.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


}
