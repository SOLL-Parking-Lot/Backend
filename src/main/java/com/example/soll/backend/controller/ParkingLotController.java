package com.example.soll.backend.controller;

import com.example.soll.backend.dto.request.CoordinatesRequest;
import com.example.soll.backend.dto.response.ParkingLotDetailResponse;
import com.example.soll.backend.dto.response.ParkingLotResponse;
import com.example.soll.backend.dto.response.SeoulParkingLotResponse;
import com.example.soll.backend.entitiy.NationalParkingLot;
import com.example.soll.backend.entitiy.SeoulParkingLot;
import com.example.soll.backend.service.NationalParkingLotService;
import com.example.soll.backend.service.ParkingLotService;
import com.example.soll.backend.service.SeoulParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/parking-lot")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;
    private final NationalParkingLotService nationalParkingLotService;
    private final SeoulParkingLotService seoulParkingLotService;

    // 좌표를 기반으로 근처에 있는 주차장을 모두 가져오는 Controller
    @PostMapping("/around")
    public ResponseEntity<List<HashMap<String,Object>>> getAroundParkingLot(@RequestBody CoordinatesRequest coordinatesRequest) {
        return ResponseEntity.ok(parkingLotService.getAroundParkingLotProcess(coordinatesRequest));
    }
    // 지도 level Zoom 크기에 따라 주차장 return
    @PostMapping("/around/{level}")
    public ResponseEntity<List<HashMap<String,Object>>> getParkingLotByMapLevel(
            @RequestBody CoordinatesRequest coordinatesRequest,
            @PathVariable int level) {
        return ResponseEntity.ok(parkingLotService.getParkingLotByLevelProcess(coordinatesRequest,level));
    }
    // 전국 일반 주차장 조회
    @GetMapping("/nationwide")
    public ResponseEntity<List<NationalParkingLot>> getAllNationalParkingLot(){
        return ResponseEntity.ok(nationalParkingLotService.getAllNationalParkingLotProcess());
    }
    // 모든 서울 주차장 조회
    @GetMapping("/seoul")
    public ResponseEntity<List<SeoulParkingLot>> getAllSeoulParkingLot(){
        return ResponseEntity.ok(seoulParkingLotService.getAllSeoulParkingLotProcess());
    }
    // 서울 주차장 조회 (실시간 주차가능 수 포함)
    @GetMapping("/seoul/{id}")
    public ResponseEntity<SeoulParkingLotResponse> getSeoulParkingLot(@PathVariable("id") Long seoulParkingId){
        return ResponseEntity.ok(seoulParkingLotService.getSeoulParkingLotAndCurrentLotCapacity(seoulParkingId));
    }

    //주차장 이름 및 주소 검색
    @GetMapping("/search")
    public ResponseEntity<List<ParkingLotResponse>> getParkingLotKeywordList(@RequestParam String keyword) {
        return ResponseEntity.ok(parkingLotService.getParkingLotKeywordList(keyword));
    }

    //주차장 상세보기
    @GetMapping("/detail")
    public ResponseEntity<ParkingLotDetailResponse> getParkingLotDetail(@RequestParam(name = "parkingLotId") Long parkingLotId, @RequestParam(name = "type") String tpye) {
        return ResponseEntity.ok(parkingLotService.getParkingLotDetail(parkingLotId,tpye));
    }   
}