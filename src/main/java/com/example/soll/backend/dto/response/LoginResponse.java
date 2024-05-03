package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken
){

}