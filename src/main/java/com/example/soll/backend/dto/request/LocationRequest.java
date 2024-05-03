package com.example.soll.backend.dto.request;

import lombok.Builder;

@Builder
public record LocationRequest(
        String originX,
        String originY,
        String destinationX,
        String destinationY) {
}
