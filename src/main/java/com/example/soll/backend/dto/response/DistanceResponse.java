package com.example.soll.backend.dto.response;

import lombok.Builder;

@Builder
public record DistanceResponse(
      Long parkingLotId,
      String type,
      double distance
) {
}
