package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record ParkingLotResponse(
    Long id,
    String parkinglotName,
    String address,
    String type
) {
} 