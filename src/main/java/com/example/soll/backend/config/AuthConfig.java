package com.example.soll.backend.config;

import com.example.soll.backend.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    private final MemberRepository memberRepository;

    // 암호 인코더 정의 - bcrypt 해싱 알고리즘 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JSON 직렬화/역직렬화 시 사용
    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper()
                // 객체의 속성 이름을 snake-case로 설정
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    //spring security에서 사용자 정보를 가져오는 빈 생성
    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> memberRepository.findByEmail(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                });
    }

    //사용자 인증을 처리하는 빈
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

}
