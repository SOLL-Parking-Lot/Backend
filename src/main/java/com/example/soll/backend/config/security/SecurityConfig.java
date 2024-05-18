package com.example.soll.backend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.soll.backend.common.utils.JwtUtil;
import com.example.soll.backend.config.security.filter.JwtAuthenticationFilter;
import com.example.soll.backend.service.AuthService;
import com.example.soll.backend.service.TokenService;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //필터체인을 구성하는 메소드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)// 보호를 비활성, 요청의 의한 인가 규칙 설정
            .authorizeHttpRequests(authorizeHttpRequest ->
                    authorizeHttpRequest
                            // UnAuth Area 인증 필요한 url패턴 설정
                            .requestMatchers("/auth/**").permitAll()
                            // Others 나머지 요청은 인증이 필요하도록 설정
                            .anyRequest().authenticated()
                )
            .sessionManagement((sessionManagement) ->
                    sessionManagement
                            // JWT 토큰 기반의 인증을 사용하기 위해 무상태 세션 정책 사용
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            //구성을 설정해서 클라이언트의 요청을 허용
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // React localhost:3000에 대해 Cors 허용 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);// credentials 허용 여부 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
}