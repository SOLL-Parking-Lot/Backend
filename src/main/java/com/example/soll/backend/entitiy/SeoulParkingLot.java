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
public class SeoulParkingLot{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEOUL_PARKINGLOT_ID")
    private Long id;
    @Column(name = "PARKINGLOT_NAME")
    private String parkingLotName; // 주차장명
    @Column(name = "ADDRESS")
    private String address; // 주소
    @Column(name = "LATITUDE")
    private Double latitude; // 주차장 위치 좌표 위도
    @Column(name = "LONGITUDE")
    private Double longitude; // 주차장 위치 좌표 경도
    @Column(name = "PARKING_TYPE")
    private String parkingType; // 주차장 종류명
    @Column(name = "OPERATION_TYPE")
    private String operationType; // 운영구분명
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber; // 전화번호
    @Column(name = "TOTAL_PARKING_SPACE")
    private Integer totalParkingSpace; // 총 주차면
    @Column(name = "FEE_TYPE")
    private String feeType; // 유무료구분명
    @Column(name = "NIGHT_FREE_OPEN")
    private String nightFreeOpen; // 야간무료개방여부명
    @Column(name = "WEEKDAY_START_TIME")
    private String weekdayStartTime; // 평일 운영 시작시각
    @Column(name = "WEEKDAY_END_TIME")
    private String weekdayEndTime; // 평일 운영 종료시각
    @Column(name = "WEEKEND_START_TIME")
    private String weekendStartTime; // 주말 운영 시작시각
    @Column(name = "WEEKEND_END_TIME")
    private String weekendEndTime; // 주말 운영 종료시각
    @Column(name = "HOLIDAY_START_TIME")
    private String holidayStartTime; // 공휴일 운영 시작시각
    @Column(name = "HOLIDAY_END_TIME")
    private String holidayEndTime; // 공휴일 운영 종료시각
    @Column(name = "SATURDAY_FEE_TYPE")
    private String saturdayFeeType; // 토요일 유,무료 구분명
    @Column(name = "HOLIDAY_FEE_TYPE")
    private String holidayFeeType; // 공휴일 유,무료 구분명
    @Column(name = "MONTHLY_TICKET_PRICE")
    private Integer monthlyTicketPrice; // 월 정기권 금액
    @Column(name = "BASIC_FEE")
    private Integer basicFee; // 기본 주차 요금
    @Column(name = "BASIC_TIME")
    private Integer basicTime; // 기본 주차 시간(분 단위)
    @Column(name = "ADDITIONAL_FEE")
    private Integer additionalFee; // 추가 단위 요금
    @Column(name = "ADDITIONAL_TIME")
    private Integer additionalTime; // 추가 단위 시간(분 단위)
    @Column(name = "MAX_DAILY_FEE")
    private Integer maxDailyFee; // 일 최대 요금

}
