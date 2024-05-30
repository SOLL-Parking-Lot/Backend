package com.example.soll.backend.service;

import com.example.soll.backend.dto.response.CurrentParkingLotResponse;
import com.example.soll.backend.entitiy.*;
import com.example.soll.backend.repository.NationalFavoriteRepository;
import com.example.soll.backend.repository.NationalParkingLotRepository;
import com.example.soll.backend.repository.SeoulFavoriteRepository;
import com.example.soll.backend.repository.SeoulParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final NationalFavoriteRepository nationalFavoriteRepository;
    private final SeoulFavoriteRepository seoulFavoriteRepository;
    private final NationalParkingLotRepository nationalParkingLotRepository;
    private final SeoulParkingLotRepository seoulParkingLotRepository;
    private final CurrentParkingLotService currentParkingLotService;

    @Transactional(readOnly = true)
    public List<HashMap<String,Object>> getAllFavoritesProcess(Long memberId){
        List<HashMap<String,Object>> allFavoriteList = new ArrayList<>();

        List<NationalFavoriate> nationalFavoriateList = nationalFavoriteRepository.findByMemberId(memberId);
        List<SeoulFavorite> seoulFavoriteList = seoulFavoriteRepository.findByMemberId(memberId);

        for(NationalFavoriate nationalFavoriate : nationalFavoriateList){
            HashMap<String,Object> dataObject = new HashMap<>();

            Optional<NationalParkingLot> targetNationalLot = nationalParkingLotRepository.findById(
                    nationalFavoriate.getNationalParkingLot().getId()
            );
            if(targetNationalLot.isPresent()){
                dataObject.put("favoriteId", nationalFavoriate.getId());
                dataObject.put("type", "National");
                dataObject.put("parking", targetNationalLot.get());
            }
            allFavoriteList.add(dataObject);
        }

        for(SeoulFavorite seoulFavorite : seoulFavoriteList){
            HashMap<String,Object> dataObject = new HashMap<>();

            Optional<SeoulParkingLot> targetSeoulLot = seoulParkingLotRepository.findById(
                    seoulFavorite.getSeoulParkingLot().getId()
            );
            if(targetSeoulLot.isPresent()){
                dataObject.put("favoriteId", seoulFavorite.getId());
                dataObject.put("type", "Seoul");
                dataObject.put("parking", targetSeoulLot.get());
                CurrentParkingLotResponse response = currentParkingLotService.getCurrentParkingByAPI(
                        targetSeoulLot.get().getAddress()
                );
                dataObject.put("currentParking", response);
            }
            allFavoriteList.add(dataObject);
        }
        return allFavoriteList;
    }

    @Transactional
    public boolean addFavoriteParkingLotByType(String type,Member member,Long parkingId){
        if (type.equals("National")){
            return addNationalFavorite(member,parkingId);
        }else if (type.equals("Seoul")){
            return addSeoulFavorite(member,parkingId);
        }else{
            return false;
        }
    }

    @Transactional
    protected boolean addNationalFavorite(Member member, Long nationalFavoriteId){
       Optional<NationalFavoriate> checkPresentItem = nationalFavoriteRepository.findByMemberIdAndNationalParkingLotId(member.getId(),nationalFavoriteId);
       if(checkPresentItem.isPresent()){
           return false;
       }
       NationalFavoriate newNationalFavorite = NationalFavoriate.builder()
               .member(member)
               .nationalParkingLot(nationalParkingLotRepository.findById(nationalFavoriteId).get())
               .build();
        nationalFavoriteRepository.save(newNationalFavorite);
        return true;
    }

    @Transactional
    protected boolean addSeoulFavorite(Member member, Long seoulFavoriteId){
        Optional<SeoulFavorite> checkPresentItem = seoulFavoriteRepository.
                findByMemberIdAndSeoulParkingLotId(member.getId(), seoulFavoriteId);
        if(checkPresentItem.isPresent()){
            return false;
        }
        SeoulFavorite newSeoulFavorite = SeoulFavorite.builder()
                .member(member)
                .seoulParkingLot(seoulParkingLotRepository.findById(seoulFavoriteId).get())
                .build();
        seoulFavoriteRepository.save(newSeoulFavorite);
        return true;
    }
    @Transactional
    public boolean deleteFavoriteParkingLotByType(String type, Member member,Long parkingId){
        if (type.equals("National")){
            return deleteNationalFavorite(member.getId(),parkingId);
        }else if (type.equals("Seoul")){
            return deleteSeoulFavorite(member.getId(), parkingId);
        }else{
            return false;
        }
    }

    @Transactional
    protected boolean deleteNationalFavorite(Long memberId,Long nationalFavoriteId){
        Optional<NationalFavoriate> targetEntity = nationalFavoriteRepository.findByMemberIdAndId(memberId, nationalFavoriteId);
        if(targetEntity.isPresent()){
            nationalFavoriteRepository.deleteById(targetEntity.get().getId());
            return true;
        }
        return false;
    }

    @Transactional
    protected boolean deleteSeoulFavorite(Long memberId,Long seoulFavoriteId){
        Optional<SeoulFavorite> targetEntity = seoulFavoriteRepository.findByMemberIdAndId(memberId,seoulFavoriteId);
        if(targetEntity.isPresent()){
            seoulFavoriteRepository.deleteById(targetEntity.get().getId());
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean checkParkingLotIsBookmark(String type,Long memberId,Long parkingId){
        if (type.equals("National")){
            Optional<NationalFavoriate> targetEntity = nationalFavoriteRepository.findByMemberIdAndNationalParkingLotId(memberId,parkingId);
            return targetEntity.isPresent();
        }else if (type.equals("Seoul")){
            Optional<SeoulFavorite> targetEntity = seoulFavoriteRepository.findByMemberIdAndSeoulParkingLotId(memberId,parkingId);
            return targetEntity.isPresent();
        }else{
            return true;
        }
    }

}
