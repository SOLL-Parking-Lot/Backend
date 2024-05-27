package com.example.soll.backend.dto.request;

public record VerifyCodeRequest(
    String email,
    String code
) {

}
