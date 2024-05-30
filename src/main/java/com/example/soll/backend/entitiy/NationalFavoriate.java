package com.example.soll.backend.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NationalFavoriate extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NATIONAL_PARKING_FAVORIATE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;  // 즐겨찾기 추가한 사용자

    @ManyToOne
    @JoinColumn(name = "NATIONAL_PARKINGLOT_ID", nullable = false)
    private NationalParkingLot nationalParkingLot;  // 해당 전국 추자장

    @Builder
    public NationalFavoriate(Member member,NationalParkingLot nationalParkingLot ) {
        this.member = member;
        this.nationalParkingLot = nationalParkingLot;
    }
}
