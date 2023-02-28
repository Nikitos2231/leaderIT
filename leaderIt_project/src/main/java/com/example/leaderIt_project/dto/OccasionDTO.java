package com.example.leaderIt_project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OccasionDTO {

    @NotNull(message = "device_id cannot be null")
    private int deviceID;

    @NotNull(message = "Occasion type cannot be null")
    @NotEmpty(message = "Occasion type cannot be empty")
    private String occasionType;

    private PayloadDTO payloadDTO;

    public OccasionDTO() {
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public PayloadDTO getPayloadDTO() {
        return payloadDTO;
    }

    public void setPayloadDTO(PayloadDTO payloadDTO) {
        this.payloadDTO = payloadDTO;
    }

    public String getOccasionType() {
        return occasionType;
    }

    public void setOccasionType(String occasionType) {
        this.occasionType = occasionType;
    }
}
