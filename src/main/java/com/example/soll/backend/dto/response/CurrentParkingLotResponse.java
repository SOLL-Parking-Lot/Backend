package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record CurrentParkingLotResponse(
    // 총 주차면수
    int totalCapacity,
    // 현재 주차된 수
    int currentParking,
    // 주차 가능 수
    int currentCapacity
) {
}
