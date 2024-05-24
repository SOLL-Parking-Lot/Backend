package com.example.soll.backend.repository;

import com.example.soll.backend.entitiy.NationalParkingLot;
import com.example.soll.backend.repository.querycustom.NationalParkingLotRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalParkingLotRepository extends JpaRepository<NationalParkingLot, Long>, NationalParkingLotRepositoryCustom {
}
