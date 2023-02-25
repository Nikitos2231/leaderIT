package com.example.leaderIt_project.dto;

import com.example.leaderIt_project.custom_annotations.CheckSerialNumber;
import com.example.leaderIt_project.custom_annotations.CheckTypeOfDevice;
import com.example.leaderIt_project.models.Occasion;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class IotDeviceDTO {

//    @NotEmpty(message = "serial number shouldn't be empty")
    @NotNull(message = "serial number shouldn't be null")
    @CheckSerialNumber(message = "serial number should have size = 10, and only consist of figures")
    private String serialNumber;

    @NotEmpty(message = "device name shouldn't be empty")
    @NotNull(message = "device name shouldn't be null")
    private String deviceName;

    @NotNull(message = "type of device can't be null")
    @CheckTypeOfDevice(message = "invalid type of device, please choose one of the follow: SMART_WATCH, SMART_FRIDGE, SMART_MOBILE")
    private String deviceType;

    public IotDeviceDTO() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
