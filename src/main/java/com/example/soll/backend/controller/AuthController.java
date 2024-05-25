package com.example.soll.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.soll.backend.dto.request.AuthRequest;
import com.example.soll.backend.dto.request.SignupRequest;
import com.example.soll.backend.dto.response.AuthResponse;
import com.example.soll.backend.service.AuthService;
import com.example.soll.backend.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest login) {
        AuthResponse loginResponse = authService.authenticate(login);
        return ResponseEntity.ok()
                .body(loginResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> createMember(@RequestBody SignupRequest memberDTO) {
        authService.createMember(memberDTO);
        return ResponseEntity.ok("회원가입이 완료 되었습니다.");
    }
    
    @PostMapping("/sign-out")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        if (token.startsWith("Bearer ")) {
            String accessToken = token.substring(7);
            tokenService.addToBlacklist(accessToken);
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
