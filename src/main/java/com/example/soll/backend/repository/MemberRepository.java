package com.example.soll.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soll.backend.entitiy.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(final String nickname);

    Optional<Member> findByEmail(final String email);

    boolean existsByEmail(String email);
}
