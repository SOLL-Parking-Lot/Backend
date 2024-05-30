package com.example.soll.backend.repository;

import com.example.soll.backend.entitiy.NationalFavoriate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NationalFavoriteRepository extends JpaRepository<NationalFavoriate,Long> {
    List<NationalFavoriate> findByMemberId(Long MemberId);
    Optional<NationalFavoriate> findByMemberIdAndNationalParkingLotId(Long memberId, Long nationalParkingLotId);
    Optional<NationalFavoriate> findByMemberIdAndId(Long memberId, Long id);
}
