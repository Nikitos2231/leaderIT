package com.example.leaderIt_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "fridge_payload")
public class FridgePayload extends Payload implements Serializable {

    @Column(name = "daily_energy_consumption")
    private int dailyEnergyConsumption;

    public FridgePayload() {
    }

    public int getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }

    public void setDailyEnergyConsumption(int dailyEnergyConsumption) {
        this.dailyEnergyConsumption = dailyEnergyConsumption;
    }
}
