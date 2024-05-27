package com.example.soll.backend.dto.request;


public record PasswordChangeRequest(
    String email,
    String newPassword
) {
    
}
