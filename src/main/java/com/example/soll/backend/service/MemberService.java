package com.example.soll.backend.service;

import com.example.soll.backend.entitiy.Member;
import com.example.soll.backend.exception.ErrorCode;
import com.example.soll.backend.exception.UserNotFoundException;
import com.example.soll.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getLoginMemberProcess(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> loginMember = memberRepository.findByEmail(authentication.getName());
        if (loginMember.isPresent()) {
            return loginMember.get();
        }else{
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
        }
    }
}
