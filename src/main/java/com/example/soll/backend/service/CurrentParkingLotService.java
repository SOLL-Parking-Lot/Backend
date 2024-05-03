package com.example.soll.backend.service;

import com.example.soll.backend.dto.response.CurrentParkingResponse;
import com.example.soll.backend.exception.APIJsonParsingException;
import com.example.soll.backend.exception.ErrorCode;
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

    // 서울 주차장 DB에 있는 주소를 통해 현재 주차가능대여 수를 가져오는 메소드
    public CurrentParkingResponse getCurrentParkingByAPI(String address){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String httpUrl = PARKING_ENDPOINT + "/1/5/" + address;
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .build();
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity,String.class);

        String body = response.getBody();
        try{
            JSONObject json = new JSONObject(body);
            JSONObject resultObject = (JSONObject) json.getJSONObject("GetParkingInfo").getJSONArray("row").get(0);
            int totalCapacity = resultObject.getInt("CAPACITY");
            int currentParking = resultObject.getInt("CUR_PARKING");
            int currentCapacity = totalCapacity - currentParking;
            return new CurrentParkingResponse(totalCapacity,currentParking,currentCapacity);
        }catch(JSONException e) {
            throw new APIJsonParsingException(ErrorCode.API_JSON_PARSING_ERROR);
        }
    }
}
