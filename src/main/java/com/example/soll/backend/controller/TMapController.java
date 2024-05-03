package com.example.soll.backend.controller;

import com.example.soll.backend.dto.request.CoordinatesRequest;
import com.example.soll.backend.dto.request.LocationRequest;
import com.example.soll.backend.dto.response.AroundParkingResponse;
import com.example.soll.backend.dto.response.CoordinatesResponse;
import com.example.soll.backend.dto.response.RouteResponse;
import com.example.soll.backend.service.TMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tmap")
@RequiredArgsConstructor
public class TMapController {

    private final TMapService tMapService;

    // 위도,경도를 입력받아 근처 주차장 정보를 return
    @GetMapping("/search")
    public ResponseEntity<List<AroundParkingResponse>> tmapSearchMethod(@RequestBody CoordinatesRequest coordinates) {
        return ResponseEntity.ok(tMapService.searchAroundParkingPlace(coordinates));
    }

    // 출발지, 도착지의 위도, 경도를 입력받아 차량,도보 시간을 return
    @GetMapping("/route")
    public ResponseEntity<RouteResponse> tmapRouteMethod(@RequestBody LocationRequest location) {
        return ResponseEntity.ok(tMapService.getAllRouteInformation(location));
    }

    // 해당 주소를 좌표로 변환하여 return
    @GetMapping("/coordinates/{address}")
    public ResponseEntity<CoordinatesResponse> tmapAddressToCoordinatesMethod(@PathVariable("address") String address) {
        return ResponseEntity.ok(tMapService.getCoordinateByFullAddress(address));
    }

    // 출발지, 도착지의 위도, 경도를 입력받아 ByteArrayResource 객체로 이미지 데이터 return
    @GetMapping("/route/image")
    public ResponseEntity<ByteArrayResource> tmapRouteImageMethod(@RequestBody LocationRequest location) {
        return ResponseEntity.ok(tMapService.getRouteImage(location));
    }

    // 좌표를 주소로 변환
    @GetMapping("/address/coordinate")
    public ResponseEntity<String> tmapCoordinateToAddressMethod(@RequestBody CoordinatesRequest coordinates) {
        return ResponseEntity.ok(tMapService.getAddressByCoordinate(coordinates));
    }
}
