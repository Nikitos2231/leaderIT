package com.example.leaderIt_project.enums;

public enum IotDeviceTypeEnum {
    SMART_MOBILE("SMART_MOBILE"), SMART_FRIDGE("SMART_FRIDGE"), SMART_WATCH("SMART_WATCH");

    private String name;
    IotDeviceTypeEnum(String name) {
        this.name = name;
    }
}
