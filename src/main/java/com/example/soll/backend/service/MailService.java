package com.example.soll.backend.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.soll.backend.entitiy.Mail;
import com.example.soll.backend.entitiy.Member;
import com.example.soll.backend.exception.ErrorCode;
import com.example.soll.backend.exception.UserNotFoundException;
import com.example.soll.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MailService {
        private final MemberRepository memberRepository;
        private final MailSender mailSender;
        private final PasswordEncoder passwordEncoder;
        private final Map<String, String> verificationCodes = new HashMap<>();

        public Mail createMailAndSendVerificationCode(String email) {
                String verificationCode = generateVerificationCode();
                saveVerificationCode(email, verificationCode);
                Mail mail = Mail.builder()
                        .title("SOLL주차장 인증번호 안내 이메일 입니다.")
                        .address(email)
                        .message("안녕하세요. SOLL주차장 인증번호 안내 관련 이메일 입니다. 회원님의 인증 번호는 " + verificationCode + " 입니다. 인증 후에 비밀번호를 변경을 해주세요.")
                        .build();
                return mail;
            }

    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    public void changePassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> 
                new UserNotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION));
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
        verificationCodes.remove(email); // 인증번호 사용 후 제거
    }

    public void mailSend(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getAddress());
        message.setSubject(mail.getTitle());
        message.setText(mail.getMessage());
        message.setFrom("sollparking@naver.com");
        message.setReplyTo("sollparking@naver.com");
        log.info("Sending email to: {}", mail.getAddress());
        mailSender.send(message);
    }

    private String generateVerificationCode() {
        char[] charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder code = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(charSet.length);
            code.append(charSet[randomIndex]);
        }
        return code.toString();
    }

    private void saveVerificationCode(String email, String verificationCode) {
        verificationCodes.put(email, verificationCode);
    }
}
