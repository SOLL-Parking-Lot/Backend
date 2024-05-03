package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record LocationResponse(
        // 출발지 좌표
        String originX,
        String originY,
        // 도착지 좌표
        String destinationX,
        String destinationY) {
}
