package com.example.leaderIt_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "mobile_payload")
public class MobilePayload extends Payload implements Serializable {

    @Column(name = "count_calls_in_day")
    private int countCallsInDay;

    public MobilePayload() {
    }

    public int getCountCallsInDay() {
        return countCallsInDay;
    }

    public void setCountCallsInDay(int countCallsInDay) {
        this.countCallsInDay = countCallsInDay;
    }
}
