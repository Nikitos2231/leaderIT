package com.example.leaderIt_project.dto;

public class WatchPaylaodDTO {

    private String secretKey;
    private int countStepsInDay;

    public WatchPaylaodDTO() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getCountStepsInDay() {
        return countStepsInDay;
    }

    public void setCountStepsInDay(int countStepsInDay) {
        this.countStepsInDay = countStepsInDay;
    }
}
