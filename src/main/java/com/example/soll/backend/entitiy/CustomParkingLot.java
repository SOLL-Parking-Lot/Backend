package com.example.soll.backend.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CustomParkingLot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOM_PARKING_ID")
    private Long id;
    @Column(name = "CUSTOM_PARKINGLOT_NAME")
    private String parkingLotName; // 주차장명
    @Column(name = "CUSTOM_CUSTOM_INFO")
    private String customInfo; //주차장 설명
    @Column(name = "LONGITUDE")
    private Double longitude; // 경도
    @Column(name = "LATITUDE")
    private Double latitude; // 위도
    @Column(name = "ADDRESS")
    private String address; // 주소
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber; // 전화번호
    @Column(name = "FEE_TYPE")
    private String feeType; // 유무료구분명

}
