package com.example.soll.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.soll.backend.common.utils.JwtUtil;
import com.example.soll.backend.dto.request.AuthRequest;
import com.example.soll.backend.dto.response.AuthResponse;
import com.example.soll.backend.entitiy.Member;
import com.example.soll.backend.repository.MemberRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManager authenticationManager;
    // private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    // 로그인 로직
    @Transactional
    public AuthResponse authenticate(AuthRequest login) {
        Member member = memberRepository.findByEmail(login.email()).get();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.email(),
                login.password()
        ));

        log.info("[BR INFO]: {} 남이 로그인에 성공하셨습니다.", member.getNickname());


        // JWT 토큰 생성
        String jwtToken = jwtUtil.generateToken(login.email());

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    } 
}