package com.example.soll.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.soll.backend.dto.request.AuthRequest;
import com.example.soll.backend.dto.request.PasswordChangeRequest;
import com.example.soll.backend.dto.request.SignupRequest;
import com.example.soll.backend.dto.request.VerifyCodeRequest;
import com.example.soll.backend.dto.response.AuthResponse;
import com.example.soll.backend.entitiy.Mail;
import com.example.soll.backend.service.AuthService;
import com.example.soll.backend.service.MailService;
import com.example.soll.backend.service.MemberService;
import com.example.soll.backend.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final MemberService memberService;
    private final MailService mailService;
    
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

    @GetMapping("/validation/{email}")
    public ResponseEntity<Boolean> validationEmail(@PathVariable("email") String email) {
        Boolean checkResult = memberService.validation(email);
        return ResponseEntity.ok(checkResult);
    }

    // 해당 메일로 인증번호 발송 
    @PostMapping("/verify-code")
    public ResponseEntity<String> sendEmail(@RequestParam(name = "email") String email) {
        Mail dto = mailService.createMailAndSendVerificationCode(email);
        mailService.mailSend(dto);
        return ResponseEntity.ok("인증번호를 발송 하였습니다.");
    }

    // 보낸 인증번호가 맞는지 확인
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        boolean isValid = mailService.verifyCode(verifyCodeRequest.email(),verifyCodeRequest.code());
        return ResponseEntity.ok(isValid);
    }

    // 비밀번호 변경
    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        mailService.changePassword(request.email(),request.newPassword());
        return ResponseEntity.ok("비밀번호를 변경하였습니다.");
    }
    
}
