package com.example.soll.backend.dto.response;

import com.example.soll.backend.dto.request.LocationRequest;
import lombok.Builder;

@Builder
public record RouteResponse(
        // 거리
        String distance,
        // 택시 요금
        int taxiFare,
        // 차량 소요 시간
        int carTotalTime,
        // 도보 소요 시간
        int roadTotalTime,
        // 왕복 소요 요금
        int totalFare,
        // 두 장소의 좌표
        LocationRequest locationRequest) {
}
