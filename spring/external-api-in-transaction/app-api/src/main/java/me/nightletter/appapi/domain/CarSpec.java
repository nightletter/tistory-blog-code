package me.nightletter.appapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CarSpec(
        @JsonProperty("STATUS") String status,
        @JsonProperty("RESPONSE") String response,
        @JsonProperty("CARVENDER") String carVender,
        @JsonProperty("CARNAME") String carName,
        @JsonProperty("SUBMODEL") String subModel,
        @JsonProperty("UID") String uid,
        @JsonProperty("CARYEAR") String carYear,
        @JsonProperty("DRIVE") String drive,
        @JsonProperty("FUEL") String fuel,
        @JsonProperty("PRICE") String price,
        @JsonProperty("CC") String cc,
        @JsonProperty("MISSION") String mission,
        @JsonProperty("CARURL") String carUrl,
        @JsonProperty("VIN") String vin,
        @JsonProperty("RESULT") String result,
        @JsonProperty("ERRMSG") String errMsg,
        @JsonProperty("FRONTTIRE") String frontTire,
        @JsonProperty("REARTIRE") String rearTire,
        @JsonProperty("EOILLITER") String eoilliter,
        @JsonProperty("WIPER") String wiper,
        @JsonProperty("SEATS") String seats,
        @JsonProperty("BATTERYLIST") List<BatteryInfo> batteryList,
        @JsonProperty("FUELECO") String fuelEco,
        @JsonProperty("FUELTANK") String fuelTank
) {
    public record BatteryInfo(
            @JsonProperty("BRAND") String brand,
            @JsonProperty("MODEL") String model,
            @JsonProperty("TYPE") String type
    ) {
    }
}