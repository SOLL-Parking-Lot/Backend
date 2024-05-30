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
public class SeoulFavorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEOUL_PARKING_FAVORIATE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;  // 즐겨찾기 추가한 사용자

    @ManyToOne
    @JoinColumn(name = "SEOUL_PARKINGLOT_ID", nullable = false)
    private SeoulParkingLot seoulParkingLot;  // 해당 서울 추자장

    @Builder
    public SeoulFavorite(Member member,SeoulParkingLot seoulParkingLot ) {
        this.member = member;
        this.seoulParkingLot = seoulParkingLot;
    }
}
