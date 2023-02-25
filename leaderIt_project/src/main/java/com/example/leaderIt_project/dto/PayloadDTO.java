package com.example.leaderIt_project.dto;

import jakarta.validation.constraints.NotNull;

public class PayloadDTO {

    private String secretKey;

    private String dailyEnergyConsumption;
    private String countCallsInDay;
    private String countStepsInDay;

    public PayloadDTO() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }

    public void setDailyEnergyConsumption(String dailyEnergyConsumption) {
        this.dailyEnergyConsumption = dailyEnergyConsumption;
    }

    public String getCountCallsInDay() {
        return countCallsInDay;
    }

    public void setCountCallsInDay(String countCallsInDay) {
        this.countCallsInDay = countCallsInDay;
    }

    public String getCountStepsInDay() {
        return countStepsInDay;
    }

    public void setCountStepsInDay(String countStepsInDay) {
        this.countStepsInDay = countStepsInDay;
    }
}
