package com.example.leaderIt_project.dto;

public class FridgePayloadDTO {

    private String secretKey;
    private int dailyEnergyConsumption;

    public FridgePayloadDTO() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }

    public void setDailyEnergyConsumption(int dailyEnergyConsumption) {
        this.dailyEnergyConsumption = dailyEnergyConsumption;
    }
}
