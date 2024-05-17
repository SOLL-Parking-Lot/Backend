package com.example.soll.backend.service;

import com.example.soll.backend.entitiy.NationalParkingLot;
import com.example.soll.backend.repository.NationalParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
