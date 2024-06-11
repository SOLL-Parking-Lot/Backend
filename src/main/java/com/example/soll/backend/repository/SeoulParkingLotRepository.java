package com.example.soll.backend.repository;

import com.example.soll.backend.entitiy.SeoulParkingLot;
import com.example.soll.backend.repository.querycustom.SeoulParkingLotRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeoulParkingLotRepository extends JpaRepository<SeoulParkingLot, Long>, SeoulParkingLotRepositoryCustom {
    Optional<SeoulParkingLot> findByAddress(String address);
}
