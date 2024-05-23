package com.example.soll.backend.repository;

import com.example.soll.backend.entitiy.CustomParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomParkingLotRepository extends JpaRepository<CustomParkingLot, Long> {
    List<CustomParkingLot> findByMemberId(Long MemberId);
}
