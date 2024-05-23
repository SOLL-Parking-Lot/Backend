package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record CustomParkingResponse(
        String parkingLotName,
        String customInfo,
        String address,
        String phoneNumber,
        String feeType
) {
}
