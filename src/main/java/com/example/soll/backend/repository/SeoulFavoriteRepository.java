package com.example.soll.backend.repository;

import com.example.soll.backend.entitiy.NationalFavoriate;
import com.example.soll.backend.entitiy.SeoulFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeoulFavoriteRepository extends JpaRepository<SeoulFavorite,Long> {
    List<SeoulFavorite> findByMemberId(Long MemberId);
    Optional<SeoulFavorite> findByMemberIdAndSeoulParkingLotId(Long MemberId, Long seoulParkingLotId);
    Optional<SeoulFavorite> findByMemberIdAndId(Long memberId, Long id);
}
