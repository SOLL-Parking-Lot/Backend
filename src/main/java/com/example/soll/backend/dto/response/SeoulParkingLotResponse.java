package com.example.soll.backend.dto.response;

import com.example.soll.backend.entitiy.SeoulParkingLot;
import lombok.Builder;

@Builder
public record SeoulParkingLotResponse(
        // 서울 주차장 정보
        SeoulParkingLot seoulParkingLot,
        // 실시간 주차가능 대수에 대한 Record
        CurrentParkingLotResponse currentParkingLotResponse
) {
}
