package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record CoordinatesResponse(
        // 위도
        double latitude,
        // 경도
        double longitude){
}
