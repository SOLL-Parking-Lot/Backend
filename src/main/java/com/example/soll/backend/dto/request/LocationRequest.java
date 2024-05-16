package com.example.soll.backend.dto.request;

import lombok.Builder;

@Builder
public record LocationRequest(
        double startLatitude,
        double startLongitude,
        double endLatitude,
        double endLongitude) {
}
