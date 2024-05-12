package com.example.soll.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soll.backend.entitiy.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //닉네임을 기준으로 멤버 조회
    Optional<Member> findByNickname(final String nickname);
    //이메일을 기준으로 멤버 조회
    Optional<Member> findByEmail(final String email);
    //이메일이 존재하는지 확인
    boolean existsByEmail(String email);
}
