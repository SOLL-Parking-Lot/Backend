package com.example.soll.backend.service;

import com.example.soll.backend.dto.response.SeoulParkingLotResponse;
import com.example.soll.backend.entitiy.SeoulParkingLot;
import com.example.soll.backend.repository.SeoulParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
}
