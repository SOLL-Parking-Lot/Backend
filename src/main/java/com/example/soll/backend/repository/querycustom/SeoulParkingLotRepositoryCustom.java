package com.example.soll.backend.repository.querycustom;

import java.util.List;

import com.example.soll.backend.entitiy.SeoulParkingLot;

public interface SeoulParkingLotRepositoryCustom {
    List<SeoulParkingLot> findTop5SeoulParkingLotBy(String keyword);
}