package com.example.soll.backend.service;

import com.example.soll.backend.dto.response.ParkingLotResponse;
import com.example.soll.backend.entitiy.NationalParkingLot;
import com.example.soll.backend.repository.NationalParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NationalParkingLotService {

    private final NationalParkingLotRepository nationalParkingLotRepository;

    @Transactional(readOnly = true)
    public List<NationalParkingLot> getAllNationalParkingLotProcess(){
        return nationalParkingLotRepository.findAll();
    }
    @Transactional(readOnly = true)
    public NationalParkingLot getNationalParkingLotByIdProcess(Long id){
        return nationalParkingLotRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ParkingLotResponse> getNationalParkingLotList(String keyword){
        try {
            return nationalParkingLotRepository.findTop5NationalParkingLotBy(keyword)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            System.err.println("Error occurred while fetching parking lot list: " + e.getMessage());
            e.printStackTrace();
            // 필요 시 예외를 다시 던지거나, 적절한 예외로 감싸서 던집니다.
            throw e; // 또는 throw new CustomException("Custom error message", e);
        }
    }

     private ParkingLotResponse convertToDTO(NationalParkingLot nationalParkingLot) {
        System.out.println(nationalParkingLot);
        return ParkingLotResponse.builder()
        .id(nationalParkingLot.getId())
        .parkinglotName(nationalParkingLot.getParkingLotName())
        .address(nationalParkingLot.getEffectiveAddress())
        .type("National")
        .build();
    }

}
