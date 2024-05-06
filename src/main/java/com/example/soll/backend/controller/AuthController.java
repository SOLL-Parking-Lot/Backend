package com.example.soll.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.soll.backend.dto.request.AuthRequest;
import com.example.soll.backend.dto.response.AuthResponse;
import com.example.soll.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest login) {
        AuthResponse loginResponse = authService.authenticate(login);
        return ResponseEntity.ok()
                .body(loginResponse);
    }
}
