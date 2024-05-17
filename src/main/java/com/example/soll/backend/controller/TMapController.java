package com.example.soll.backend.controller;

import com.example.soll.backend.dto.request.CoordinatesRequest;
import com.example.soll.backend.dto.response.CoordinatesResponse;
import com.example.soll.backend.service.TMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tmap")
@RequiredArgsConstructor
public class TMapController {

    private final TMapService tMapService;

    // 해당 주소를 좌표로 변환하여 return
    @GetMapping("/coordinates/{address}")
    public ResponseEntity<CoordinatesResponse> addressToCoordinatesMethod(@PathVariable("address") String address) {
        return ResponseEntity.ok(tMapService.getCoordinateByFullAddress(address));
    }
    // 좌표를 주소로 변환
    @GetMapping("/address/coordinate")
    public ResponseEntity<String> coordinateToAddressMethod(@RequestBody CoordinatesRequest coordinates) {
        return ResponseEntity.ok(tMapService.getAddressByCoordinate(coordinates));
    }
}
