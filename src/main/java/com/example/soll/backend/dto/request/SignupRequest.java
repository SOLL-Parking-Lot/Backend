package com.example.soll.backend.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignupRequest(
        @NotBlank(message = "이메일을 입력해주세요")
        String email,
        String password,
        @Size(max = 6, message = "닉네임은 6자리까지만 입력해 주세요")
        @NotBlank(message = "닉네임을 입력해주세요")
        String nickname
) {
}
