package com.example.soll.backend.controller;

import com.example.soll.backend.service.FavoriteService;
import com.example.soll.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/parking-lot/bookmark")
@RequiredArgsConstructor
public class FavoriteController {

    private final MemberService memberService;
    private final FavoriteService favoriteService;

    @GetMapping("/is-bookmark")
    public ResponseEntity<Long> getParkingLotIsBookmark(
            @RequestParam("type") String type,
            @RequestParam("parkingId") Long parkingId){
        return ResponseEntity.ok(favoriteService.checkParkingLotIsBookmark(
                type,memberService.getLoginMemberProcess().getId(),parkingId
        ));
    }

    @GetMapping("")
    public ResponseEntity<List<HashMap<String,Object>>> getAllFavoriteList(){
        return ResponseEntity.ok(favoriteService.getAllFavoritesProcess(
                memberService.getLoginMemberProcess().getId()
        ));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> addFavorite(
            @RequestParam("type") String type,
            @RequestParam("parkingId") Long parkingId){
        return ResponseEntity.ok(favoriteService.addFavoriteParkingLotByType(
                type,memberService.getLoginMemberProcess(),parkingId
        ));
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteFavorite(
            @RequestParam("type") String type,
            @RequestParam("favoriteId") Long favoriteId){
        return ResponseEntity.ok(favoriteService.deleteFavoriteParkingLotByType(
                type,memberService.getLoginMemberProcess(),favoriteId
        ));
    }
}
