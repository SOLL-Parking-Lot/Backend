package com.example.soll.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomParkingRequest {
    private String parkingLotName;
    private String customInfo;
    private String address;
    private String phoneNumber;
    private String feeType;

    @JsonCreator
    public CustomParkingRequest(
            @JsonProperty("parkingLotName") String parkingLotName,
            @JsonProperty("customInfo") String customInfo,
            @JsonProperty("address") String address,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("feeType") String feeType) {
        this.parkingLotName = parkingLotName;
        this.customInfo = customInfo;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.feeType = feeType;
    }
}
