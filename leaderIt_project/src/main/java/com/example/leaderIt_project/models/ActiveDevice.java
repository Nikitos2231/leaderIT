package com.example.leaderIt_project.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_active_list")
public class ActiveDevice {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "date_first_active")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateFirstActive;

    @Column(name = "date_last_active")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateLastActive;

    public ActiveDevice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateFirstActive() {
        return dateFirstActive;
    }

    public void setDateFirstActive(LocalDateTime dateFirstActive) {
        this.dateFirstActive = dateFirstActive;
    }

    public LocalDateTime getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(LocalDateTime dateLastActive) {
        this.dateLastActive = dateLastActive;
    }
}
