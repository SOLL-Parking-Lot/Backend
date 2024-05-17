package com.example.soll.backend.service;

import com.example.soll.backend.dto.response.CurrentParkingLotResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CurrentParkingLotService {

    @Value("${api.seoul-parking.endpoint}")
    private String PARKING_ENDPOINT;

    // 서울 주차장 DB에 있는 주소를 통해 현재 주차가능 대여 수를 가져오는 메소드
    public CurrentParkingLotResponse getCurrentParkingByAPI(String address){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String httpUrl = PARKING_ENDPOINT + "/1/10/" + address;
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .build();
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity,String.class);

        String body = response.getBody();
        try{
            JSONObject json = new JSONObject(body);
            JSONArray resultArray = json.getJSONObject("GetParkingInfo").getJSONArray("row");
            int totalCapacity = 0;
            int currentParking = 0;
            int currentCapacity = 0;
            for(Object object : resultArray){
                JSONObject resultObject = (JSONObject) object;
                totalCapacity = resultObject.getInt("CAPACITY");
                currentParking = resultObject.getInt("CUR_PARKING");
                currentCapacity = totalCapacity - currentParking;
                if (currentCapacity > 0){
                    break;
                }
            }
            if (currentCapacity < 0){
                currentParking = 0;
                currentCapacity = totalCapacity;
            }
            return new CurrentParkingLotResponse(totalCapacity,currentParking,currentCapacity);
        }catch(JSONException e) {
            return new CurrentParkingLotResponse(0,0,0);
        }
    }
}
