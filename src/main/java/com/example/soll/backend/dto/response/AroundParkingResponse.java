package com.example.soll.backend.dto.response;

import lombok.Builder;

// Tmap API를 통해 얻어온 근처 주차장 정보
// 이 데이터와 DB에 저장되어 있는 주차장 정보(위도,경도)를 비교하여
// DB에 저장되어 있는 주차장 정보를 가져온다.
@Builder
public record AroundParkingResponse(
    // 장소 이름
    String placeName,
    // 장소 중심 좌표
    String placeLatitude,
    String placeLongitude
) {
}
