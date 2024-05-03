package com.example.soll.backend.controller;

import com.example.soll.backend.dto.request.CoordinatesRequest;
import com.example.soll.backend.dto.request.LocationRequest;
import com.example.soll.backend.dto.response.AroundParkingResponse;
import com.example.soll.backend.dto.response.AroundParkingTotalResponse;
import com.example.soll.backend.dto.response.CoordinatesResponse;
import com.example.soll.backend.entitiy.ParkingInfo;
import com.example.soll.backend.service.TMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parking-lot")
@RequiredArgsConstructor
@Slf4j
public class ParkingLotController {

    private final TMapService tMapService;

    // 주소를 기반으로 근처에 있는 주차장을 모두 가져오는 Controller
    @GetMapping("/around/parking/{address}")
    public ResponseEntity<List<AroundParkingTotalResponse>> getParkingAllInformation(@PathVariable("address") String address){

        List<AroundParkingTotalResponse> aroundParkingTotalResponses = new ArrayList<>();

        CoordinatesResponse coordinates = tMapService.getCoordinateByFullAddress(address);

        List<AroundParkingResponse> aroundParkingResponses = tMapService.searchAroundParkingPlace(new CoordinatesRequest(coordinates.latitude(), coordinates.longitude()));

        for(AroundParkingResponse aroundParkingResponse : aroundParkingResponses){
            LocationRequest location = new LocationRequest(
                    coordinates.latitude(),
                    coordinates.longitude(),
                    aroundParkingResponse.placeLatitude(),
                    aroundParkingResponse.placeLongitude()
            );
            // 주차장 정보
            // AroundParkingResponse의 위도,경도를 통해
            // DB에 저장되어있는 Parking 정보 가져와서 Setting 하는 로직 필요
            AroundParkingTotalResponse allResponse = new AroundParkingTotalResponse(
                    // 주차장은 현재 demo 데이터입니다.
                    new ParkingInfo(
                        aroundParkingResponse.placeName(),
                        aroundParkingResponse.placeLatitude(),
                        aroundParkingResponse.placeLongitude()
                    ),
                    // 거리 및 차량,도보 소요시간 정보
                    tMapService.getAllRouteInformation(location),
                    // 지도 이미지
                    tMapService.getRouteImage(location).getByteArray()
            );
            aroundParkingTotalResponses.add(allResponse);
        }
        log.info("response size-{}",aroundParkingTotalResponses.size());
        return ResponseEntity.ok(aroundParkingTotalResponses);
    }
}
