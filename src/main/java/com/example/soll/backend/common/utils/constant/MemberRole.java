package com.example.soll.backend.common.utils.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    //역할 생성
    ADMIN("관리자"),
    MEMBER("회원"),
    WITHDRAW("탈퇴");

    private final String role;

    public static MemberRole from(final String role) {
        return Arrays.stream(values())
                .filter(value -> value.role.equalsIgnoreCase(role))
                .findFirst().get();
    }
}
