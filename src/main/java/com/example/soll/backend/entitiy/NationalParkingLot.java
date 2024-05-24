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
public class NationalParkingLot{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NATIONAL_PARKINGLOT_ID")
    private Long id;
    @Column(name = "PARKINGLOT_NAME")
    private String parkingLotName; // 주차장명
    @Column(name = "LONGITUDE")
    private Double longitude; // 경도
    @Column(name = "LATITUDE")
    private Double latitude; // 위도
    @Column(name = "PARKING_TYPE")
    private String parkingType; // 주차장구분
    @Column(name = "PARKING_CATEGORY")
    private String parkingCategory; // 주차장유형
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber; // 연락처
    @Column(name = "LOT_NUMBER_ADDRESS")
    private String lotNumberAddress; // 주차장지번주소
    @Column(name = "ROAD_NAME_ADDRESS")
    private String roadNameAddress; // 주차장도로명주소
    @Column(name = "TOTAL_PARKING_SPACE")
    private Integer totalParkingSpace; // 주차구획수
    @Column(name = "OPERATING_DAYS")
    private String operatingDays; // 운영요일
    @Column(name = "WEEKDAY_START_TIME")
    private String weekdayStartTime; // 평일운영시작시각
    @Column(name = "WEEKDAY_END_TIME")
    private String weekdayEndTime; // 평일운영종료시각
    @Column(name = "SATURDAY_START_TIME")
    private String saturdayStartTime; // 토요일운영시작시각
    @Column(name = "SATURDAY_END_TIME")
    private String saturdayEndTime; // 토요일운영종료시각
    @Column(name = "HOLIDAY_START_TIME")
    private String holidayStartTime; // 공휴일운영시작시각
    @Column(name = "HOLIDAY_END_TIME")
    private String holidayEndTime; // 공휴일운영종료시각
    @Column(name = "FEE_INFO")
    private String feeInfo; // 요금정보

    //도로명 주소가 있으면 도로명 주소 없으면 지번 주소 반환
    public String getEffectiveAddress() {
        return (roadNameAddress != null && !roadNameAddress.isEmpty()) ? roadNameAddress : lotNumberAddress;
    }

}