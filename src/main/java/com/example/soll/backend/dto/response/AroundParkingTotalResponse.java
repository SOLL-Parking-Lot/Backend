package com.example.soll.backend.dto.response;

import com.example.soll.backend.entitiy.ParkingInfo;

import lombok.NoArgsConstructor;

// 모든 주차장 정보를 포함하는 레코드
// 기본 주차장 정보
// 도보, 차량 시간 정보
// 해당 장소까지의 지도 정보
public record AroundParkingTotalResponse(
    ParkingInfo parkingInfo,
    RouteResponse routeResponse,
    byte[] routeImage
) {

}
