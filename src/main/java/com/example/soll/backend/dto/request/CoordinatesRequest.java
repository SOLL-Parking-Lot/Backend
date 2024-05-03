package com.example.soll.backend.dto.request;

import lombok.Builder;

@Builder
public record CoordinatesRequest(String latitude, String longitude) {
}
