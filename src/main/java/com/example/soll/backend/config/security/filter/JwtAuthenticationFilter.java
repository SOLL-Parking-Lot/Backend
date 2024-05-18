package com.example.soll.backend.config.security.filter;

import com.example.soll.backend.common.JwtAuthenticationToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.soll.backend.common.utils.JwtUtil;
import com.example.soll.backend.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, TokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    //http 요청에 대해 한 번 씩 호출되는 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        //authorization 헤더를 추출 헤더에 jwt토큰이 있는지 확인 
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractUsername(jwt);
        }
        
        //jwt 토큰에서 사용자 이메일을 추출하고 토큰의 유효성을 검증
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt, email)) {
                // JWT 유효성 검사를 통과한 경우, Spring Security 인증 객체 생성
                Authentication authentication = new JwtAuthenticationToken(email);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
