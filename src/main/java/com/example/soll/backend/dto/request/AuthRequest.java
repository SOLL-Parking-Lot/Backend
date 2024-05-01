package com.example.soll.backend.dto.request;

import lombok.Builder;

@Builder
public record AuthRequest(
        String email,
        String password
) {
}
