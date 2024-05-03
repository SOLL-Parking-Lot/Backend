package com.example.soll.backend.service;


import com.example.soll.backend.dto.request.CoordinatesRequest;
import com.example.soll.backend.dto.request.LocationRequest;
import com.example.soll.backend.dto.response.AroundParkingResponse;
import com.example.soll.backend.dto.response.CoordinatesResponse;
import com.example.soll.backend.dto.response.RouteResponse;
import com.example.soll.backend.exception.APIJsonParsingException;
import com.example.soll.backend.exception.ErrorCode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;


@Service
public class TMapService {

    @Value("${api.tmap.app-key}")
    private String TMAP_APP_KEY;
    @Value("${api.tmap.search-around-url}")
    private String TMAP_AROUND_SEARCH_URL;
    @Value("${api.tmap.car-route.url}")
    private String TMAP_CAR_ROUTE_URL;
    @Value("${api.tmap.road-route.url}")
    private String TMAP_ROAD_ROUTE_URL;
    @Value("${api.tmap.address-to-local.url}")
    private String TMAP_ADDRESS_TO_LOCAL_URL;
    @Value("${api.tmap.map-route.url}")
    private String TMAP_MAP_ROUTE_URL;
    @Value("${api.tmap.local-to-address.url}")
    private String TMAP_LOCAL_TO_ADDRESS_URL;

    public String getAddressByCoordinate(CoordinatesRequest coordinates){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("appKey",TMAP_APP_KEY);
        httpHeaders.set("Content-Type","application/json");
        httpHeaders.set("Accept","application/json");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(TMAP_LOCAL_TO_ADDRESS_URL)
                .queryParam("lon", coordinates.longitude())
                .queryParam("lat",coordinates.latitude())
                .build();
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity,String.class);
        String body = response.getBody();
        try{
            JSONObject jsonObject = new JSONObject(body);
            JSONObject addressInfo = jsonObject.getJSONObject("addressInfo");
            return addressInfo.getString("fullAddress");
        }catch(JSONException e){
            throw new APIJsonParsingException(ErrorCode.API_JSON_PARSING_ERROR);
        }
    }
    public CoordinatesResponse getCoordinateByFullAddress(String address){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("appKey",TMAP_APP_KEY);
        httpHeaders.set("Content-Type","application/json");
        httpHeaders.set("Accept","application/json");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(TMAP_ADDRESS_TO_LOCAL_URL)
                .queryParam("fullAddr", address)
                .build();
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity,String.class);
        String body = response.getBody();
        try{
            JSONObject jsonObject = new JSONObject(body);
            JSONArray coordinateInfo = jsonObject.getJSONObject("coordinateInfo").getJSONArray("coordinate");
            JSONObject coordinateObject = (JSONObject) coordinateInfo.get(0);
            String latitude = coordinateObject.getString("newLon");
            String longitude = coordinateObject.getString("newLat");
            return new CoordinatesResponse(latitude,longitude);
        }catch(JSONException e){
            throw new APIJsonParsingException(ErrorCode.API_JSON_PARSING_ERROR);
        }
    }
    public RouteResponse getAllRouteInformation(LocationRequest location){

        int roadTotalTime = 0;
        int carTotalTime = 0;
        String distance = "";
        int taxiFare = 0;
        int totalFare = 0;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("appKey",TMAP_APP_KEY);
        httpHeaders.set("Content-Type","application/json");
        httpHeaders.set("Accept","application/json");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents roadUriComponents = UriComponentsBuilder
                .fromHttpUrl(TMAP_ROAD_ROUTE_URL)
                .queryParam("startX",location.originX())
                .queryParam("startY",location.originY())
                .queryParam("endX",location.destinationX())
                .queryParam("endY",location.destinationY())
                .queryParam("startName", "startLocation")
                .queryParam("endName","endLocation")
                .build();
        UriComponents carUriComponents = UriComponentsBuilder
                .fromHttpUrl(TMAP_CAR_ROUTE_URL)
                .queryParam("startX",location.originX())
                .queryParam("startY",location.originY())
                .queryParam("endX",location.destinationX())
                .queryParam("endY",location.destinationY())
                .queryParam("startName", "startLocation")
                .queryParam("endName","endLocation")
                .build();

        ResponseEntity<String> roadResponse = restTemplate.exchange(roadUriComponents.toString(), HttpMethod.GET, entity,String.class);
        ResponseEntity<String> carResponse = restTemplate.exchange(carUriComponents.toString(), HttpMethod.GET, entity,String.class);
        String roadDataBody = roadResponse.getBody();
        String carDataBody = carResponse.getBody();
        try{
            JSONObject roadJson = new JSONObject(roadDataBody);
            JSONArray roadJsonArray = roadJson.getJSONArray("features");
            JSONObject propertiesObject = roadJsonArray.getJSONObject(0);
            int totalTime = propertiesObject.getJSONObject("properties").getInt("totalTime");
            roadTotalTime = Math.round(totalTime / 60.0f);

            JSONObject carJson = new JSONObject(carDataBody);
            JSONArray carJsonArray = carJson.getJSONArray("features");
            propertiesObject = carJsonArray.getJSONObject(0).getJSONObject("properties");

            taxiFare = propertiesObject.getInt("taxiFare");
            carTotalTime = propertiesObject.getInt("totalTime") / 60;
            totalFare = propertiesObject.getInt("totalFare");
            int distanceByInteger = propertiesObject.getInt("totalDistance");
            if (distanceByInteger >= 1000){
                distanceByInteger = Math.round(distanceByInteger / 1000.f);
                distance = distanceByInteger+"km";
            }else {
                distance = distanceByInteger + "m";
            }
        }catch(JSONException e) {
            throw new APIJsonParsingException(ErrorCode.API_JSON_PARSING_ERROR);
        }
        return new RouteResponse(distance,taxiFare,carTotalTime,roadTotalTime,totalFare, location);
    }
    public List<AroundParkingResponse> searchAroundParkingPlace(CoordinatesRequest coordinates){

        List<AroundParkingResponse> resultData = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("appKey",TMAP_APP_KEY);
        httpHeaders.set("Content-Type","application/json");
        httpHeaders.set("Accept","application/json");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(TMAP_AROUND_SEARCH_URL)
                .queryParam("categories","주차장")
                .queryParam("centerLon",Float.parseFloat(coordinates.latitude()))
                .queryParam("centerLat",Float.parseFloat(coordinates.longitude()))
                .queryParam("page",2)
                .queryParam("count",10)
                .queryParam("radius",5)
                .queryParam("sort","distance")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);
        String body = response.getBody();
        try{
            JSONObject json = new JSONObject(body);
            JSONObject resultObject = json.getJSONObject("searchPoiInfo");
            JSONObject poisObject = resultObject.getJSONObject("pois");
            JSONArray poiArray = poisObject.getJSONArray("poi");
            poiArray.forEach(poi -> {
                JSONObject poiObject = (JSONObject) poi;
                String placeName = poiObject.getString("name");
                String placeLatitude = poiObject.getString("noorLon");
                String placeLongitude = poiObject.getString("noorLat");
                resultData.add(new AroundParkingResponse(placeName,placeLatitude,placeLongitude));
            });
        }catch(JSONException e) {
            throw new APIJsonParsingException(ErrorCode.API_JSON_PARSING_ERROR);
        }
        return resultData;
    }
    public ByteArrayResource getRouteImage(LocationRequest location){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type","application/json");
        httpHeaders.set("Accept","application/json");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(TMAP_MAP_ROUTE_URL)
                .queryParam("appKey", TMAP_APP_KEY)
                .queryParam("endX", Float.parseFloat(location.destinationX()))
                .queryParam("endY", Float.parseFloat(location.destinationY()))
                .queryParam("startX", Float.parseFloat(location.originX()))
                .queryParam("startY", Float.parseFloat(location.originY()))
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);
        byte[] routeImage = response.toString().getBytes();
        return new ByteArrayResource(routeImage);
    }
}
