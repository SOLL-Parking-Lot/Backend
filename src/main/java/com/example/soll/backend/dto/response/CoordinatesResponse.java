package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record CoordinatesResponse(
        // 위도
        String latitude,
        // 경도
        String longitude){
}
