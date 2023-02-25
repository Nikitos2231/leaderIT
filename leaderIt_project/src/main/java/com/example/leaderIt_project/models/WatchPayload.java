package com.example.leaderIt_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "watch_payload")
public class WatchPayload extends Payload implements Serializable {

    @Column(name = "count_steps_in_day")
    private int countStepsInDay;

    public WatchPayload() {
    }

    public int getCountStepsInDay() {
        return countStepsInDay;
    }

    public void setCountStepsInDay(int countStepsInDay) {
        this.countStepsInDay = countStepsInDay;
    }
}
