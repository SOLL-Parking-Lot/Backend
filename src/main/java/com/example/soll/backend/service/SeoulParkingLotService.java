package com.example.soll.backend.service;

import com.example.soll.backend.dto.response.ParkingLotResponse;
import com.example.soll.backend.dto.response.SeoulParkingLotResponse;
import com.example.soll.backend.entitiy.NationalParkingLot;
import com.example.soll.backend.entitiy.SeoulParkingLot;
import com.example.soll.backend.repository.SeoulParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeoulParkingLotService {

    private final SeoulParkingLotRepository seoulParkingLotRepository;
    private final CurrentParkingLotService currentParkingLotService;

    @Transactional(readOnly = true)
    public List<SeoulParkingLot> getAllSeoulParkingLotProcess(){
        return seoulParkingLotRepository.findAll();
    }
    @Transactional(readOnly = true)
    public SeoulParkingLot getSeoulParkingLotByIdProcess(Long id){
        return seoulParkingLotRepository.findById(id).orElse(null);
    }
    @Transactional(readOnly = true)
    public SeoulParkingLotResponse getSeoulParkingLotAndCurrentLotCapacity(Long id){
        SeoulParkingLot seoulParkingLot = getSeoulParkingLotByIdProcess(id);
        return new SeoulParkingLotResponse(
                seoulParkingLot,
                currentParkingLotService.getCurrentParkingByAPI(seoulParkingLot.getAddress())
        );
    }

    @Transactional(readOnly = true)
    public List<ParkingLotResponse> getSeoulParkingLotList(String keyword){
        try {
            return seoulParkingLotRepository.findTop5SeoulParkingLotBy(keyword)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            System.err.println("Error occurred while fetching parking lot list: " + e.getMessage());
            // 예외를 다시 던지거나 적절한 처리를 합니다.
            e.printStackTrace();
            // 필요 시 예외를 다시 던지거나, 적절한 예외로 감싸서 던집니다.
            throw e; // 또는 throw new CustomException("Custom error message", e);
        }
    }

     private ParkingLotResponse convertToDTO(SeoulParkingLot seoulParkingLot) {
        System.out.println(seoulParkingLot);
        return ParkingLotResponse.builder()
        .id(seoulParkingLot.getId())
        .parkinglotName(seoulParkingLot.getParkingLotName())
        .address(seoulParkingLot.getAddress())
        .type("Seoul")
        .build();
    }
}
