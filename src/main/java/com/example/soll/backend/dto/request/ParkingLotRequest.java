package com.example.soll.backend.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;

@Builder
public record ParkingLotRequest(
    Long parkingLotId,
    String type
) {

}
