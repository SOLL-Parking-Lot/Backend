package com.example.soll.backend.service;

import com.example.soll.backend.dto.request.CustomParkingRequest;
import com.example.soll.backend.dto.response.CoordinatesResponse;
import com.example.soll.backend.entitiy.CustomParkingLot;
import com.example.soll.backend.entitiy.Member;
import com.example.soll.backend.repository.CustomParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomParkingLotService {

    private final CustomParkingLotRepository customParkingLotRepository;
    private final TMapService tMapService;

    @Transactional(readOnly = true)
    public List<CustomParkingLot> getAllCustomParkingLotByMemberIdProcess(Long memberId){
        return customParkingLotRepository.findByMemberId(memberId);
    }
    @Transactional(readOnly = true)
    public CustomParkingLot getCustomParkingLotByIdProcess(Long id){
        return customParkingLotRepository.findById(id).orElse(null);
    }

    @Transactional
    public CustomParkingLot addCustomParkingProcess(Member member, CustomParkingRequest request){
        CoordinatesResponse coordinatesResponse = tMapService.getCoordinateByFullAddress(request.getAddress());
        CustomParkingLot newCustomParkingLot = CustomParkingLot.builder()
                .parkingLotName(request.getParkingLotName())
                .customInfo(request.getCustomInfo())
                .feeType(request.getFeeType())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .latitude(coordinatesResponse.longitude())
                .longitude(coordinatesResponse.latitude())
                .member(member).build();
        return customParkingLotRepository.save(newCustomParkingLot);
    }

    @Transactional
    public boolean deleteCustomParkingProcess(Long customParkingId){
        customParkingLotRepository.deleteById(customParkingId);
        return true;
    }
}
