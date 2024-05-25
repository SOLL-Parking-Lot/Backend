package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record ParkingLotDetailResponse(
    String parkingLotName,
    Double longitude,
    Double latitude,
    String address,
    String weekdayStartTime,
    String weekdayEndTime,
    String weekendStartTime,
    String weekendEndTime,
    String holidayStartTime,
    String holidayEndTime,
    Integer basicFee,
    Integer basicTime,
    String saturdayFeeType,
    String holidayFeeType,
    String phoneNumber,
    Integer totalParkingSpace,
    String parkingType
) {

}
