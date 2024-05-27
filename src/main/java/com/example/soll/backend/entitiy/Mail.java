package com.example.soll.backend.entitiy;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Mail {
    private String address;
    private String title;
    private String message;
}