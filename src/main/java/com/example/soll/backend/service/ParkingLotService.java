package com.example.soll.backend.service;

import com.example.soll.backend.dto.request.CoordinatesRequest;
import com.example.soll.backend.dto.request.LocationRequest;
import com.example.soll.backend.dto.response.CurrentParkingLotResponse;
import com.example.soll.backend.dto.response.DistanceResponse;
import com.example.soll.backend.entitiy.CustomParkingLot;
import com.example.soll.backend.entitiy.NationalParkingLot;
import com.example.soll.backend.entitiy.SeoulParkingLot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final NationalParkingLotService nationalParkingLotService;
    private final SeoulParkingLotService seoulParkingLotService;
    private final CurrentParkingLotService currentParkingLotService;
    private final TMapService tMapService;
    private final MemberService memberService;
    private final CustomParkingLotService customParkingLotService;

    private List<DistanceResponse> sortAllParkingByDistanceProcess(CoordinatesRequest coordinatesRequest){

        Long loginMemberId = memberService.getLoginMemberProcess().getId();

        List<DistanceResponse> distanceResponses = new ArrayList<>();
        List<NationalParkingLot> allNationalList = nationalParkingLotService.getAllNationalParkingLotProcess();
        List<SeoulParkingLot> allSeoulList = seoulParkingLotService.getAllSeoulParkingLotProcess();
        List<CustomParkingLot> allCustomList = customParkingLotService.getAllCustomParkingLotByMemberIdProcess(loginMemberId);

        for (NationalParkingLot nationalParkingLot : allNationalList) {
            LocationRequest request = new LocationRequest(
                    coordinatesRequest.latitude(),
                    coordinatesRequest.longitude(),
                    nationalParkingLot.getLatitude(),
                    nationalParkingLot.getLongitude()
            );
            distanceResponses.add(
                    new DistanceResponse(
                            nationalParkingLot.getId(),
                            "National",
                            getDistanceProcess(request)
                    )
            );
        }
        for (SeoulParkingLot seoulParkingLot : allSeoulList) {
            LocationRequest request = new LocationRequest(
                    coordinatesRequest.latitude(),
                    coordinatesRequest.longitude(),
                    seoulParkingLot.getLatitude(),
                    seoulParkingLot.getLongitude()
            );
            distanceResponses.add(
                    new DistanceResponse(
                            seoulParkingLot.getId(),
                            "Seoul",
                            getDistanceProcess(request)
                    )
            );
        }
        for (CustomParkingLot customParkingLot : allCustomList) {
            LocationRequest request = new LocationRequest(
                    coordinatesRequest.latitude(),
                    coordinatesRequest.longitude(),
                    customParkingLot.getLatitude(),
                    customParkingLot.getLongitude()
            );
            distanceResponses.add(
                    new DistanceResponse(
                            customParkingLot.getId(),
                            "Custom",
                            getDistanceProcess(request)
                    )
            );
        }
        distanceResponses.sort(Comparator.comparingDouble(DistanceResponse::distance));
        return distanceResponses;
    }
    public List<HashMap<String, Object>> getParkingLotByLevelProcess(CoordinatesRequest coordinatesRequest,int level) {

        List<DistanceResponse> sortByDistanceList = sortAllParkingByDistanceProcess(coordinatesRequest);
        return getParkingLotByType(
                false,
                coordinatesRequest,
                sortByDistanceList.subList(0, Math.min(level * 25, sortByDistanceList.size()))
        );

    }

    public List<HashMap<String, Object>> getAroundParkingLotProcess(CoordinatesRequest coordinatesRequest) {

        List<DistanceResponse> sortByDistanceList = sortAllParkingByDistanceProcess(coordinatesRequest);
        return getParkingLotByType(
                true,
                coordinatesRequest,
                sortByDistanceList.subList(0, Math.min(5, sortByDistanceList.size()))
        );
    }

    private List<HashMap<String, Object>> getParkingLotByType(boolean isContainsRoute ,CoordinatesRequest coordinatesRequest, List<DistanceResponse> distanceResponses) {
        List<HashMap<String, Object>> mapList = new ArrayList<>();

        for (DistanceResponse entity : distanceResponses) {
            HashMap<String, Object> parkingLotMap = new HashMap<>();

            if (entity.type().equals("National")) {
                NationalParkingLot nationalParkingLot = nationalParkingLotService.getNationalParkingLotByIdProcess(entity.parkingLotId());
                parkingLotMap.put("type", "National");
                parkingLotMap.put("parking", nationalParkingLot);
                if (isContainsRoute) {
                    parkingLotMap.put("route",
                            tMapService.getAllRouteInformation(
                                    new LocationRequest(coordinatesRequest.latitude(),coordinatesRequest.longitude(),
                                            nationalParkingLot.getLatitude(), nationalParkingLot.getLongitude()
                                    )
                            )
                    );
                }

            } else if (entity.type().equals("Seoul")) {
                SeoulParkingLot seoulParkingLot = seoulParkingLotService.getSeoulParkingLotByIdProcess(entity.parkingLotId());
                CurrentParkingLotResponse response = currentParkingLotService.getCurrentParkingByAPI(
                        seoulParkingLot.getAddress()
                );
                parkingLotMap.put("type", "Seoul");
                parkingLotMap.put("parking", seoulParkingLot);
                parkingLotMap.put("currentParking", response);
                if (isContainsRoute){
                    parkingLotMap.put("route",
                            tMapService.getAllRouteInformation(
                                    new LocationRequest(coordinatesRequest.latitude(),coordinatesRequest.longitude(),
                                            seoulParkingLot.getLatitude(), seoulParkingLot.getLongitude()
                                    )
                            )
                    );
                }
            }else if (entity.type().equals("Custom")){
                CustomParkingLot customParkingLot = customParkingLotService.getCustomParkingLotByIdProcess(entity.parkingLotId());
                parkingLotMap.put("type", "Custom");
                parkingLotMap.put("parking", customParkingLot);
                if (isContainsRoute) {
                    parkingLotMap.put("route",
                            tMapService.getAllRouteInformation(
                                    new LocationRequest(coordinatesRequest.latitude(),coordinatesRequest.longitude(),
                                            customParkingLot.getLatitude(), customParkingLot.getLongitude()
                                    )
                            )
                    );
                }
            }
            mapList.add(parkingLotMap);
        }
        return mapList;
    }

    private double getDistanceProcess(LocationRequest locationRequest) {
        double theta = locationRequest.endLongitude() - locationRequest.startLongitude();
        double dist = Math.sin(deg2rad(locationRequest.startLatitude())) *
                Math.sin(deg2rad(locationRequest.endLatitude())) +
                Math.cos(deg2rad(locationRequest.startLatitude())) *
                        Math.cos(deg2rad(locationRequest.endLatitude())) *
                        Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;
        return dist / 1000;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
