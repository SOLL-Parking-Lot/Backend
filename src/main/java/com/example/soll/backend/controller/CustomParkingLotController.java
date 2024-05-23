package com.example.soll.backend.controller;


import com.example.soll.backend.dto.request.CustomParkingRequest;
import com.example.soll.backend.entitiy.CustomParkingLot;
import com.example.soll.backend.service.CustomParkingLotService;
import com.example.soll.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/custom")
@RequiredArgsConstructor
public class CustomParkingLotController {

    private final CustomParkingLotService customParkingLotService;
    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<CustomParkingLot>> getCustomParkingList(){
        return ResponseEntity.ok(customParkingLotService.getAllCustomParkingLotByMemberIdProcess(
                memberService.getLoginMemberProcess().getId()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomParkingLot> getCustomParkingLotById(@PathVariable Long id){
        return ResponseEntity.ok(customParkingLotService.getCustomParkingLotByIdProcess(id));
    }

    @PostMapping("")
    public ResponseEntity<CustomParkingLot> addCustomParkingLot(@RequestBody CustomParkingRequest request){
        return ResponseEntity.ok(
                customParkingLotService.addCustomParkingProcess(
                        memberService.getLoginMemberProcess(),
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCustomParkingLot(@PathVariable Long id){
        return ResponseEntity.ok(customParkingLotService.deleteCustomParkingProcess(id));
    }
}
