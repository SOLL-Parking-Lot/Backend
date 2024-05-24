package com.example.soll.backend.repository.querycustom;

import java.util.List;

import com.example.soll.backend.entitiy.NationalParkingLot;

public interface NationalParkingLotRepositoryCustom {
    public List<NationalParkingLot> findTop5NationalParkingLotBy(String keyword);
}
