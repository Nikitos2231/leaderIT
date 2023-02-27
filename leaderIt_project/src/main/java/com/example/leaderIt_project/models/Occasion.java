package com.example.leaderIt_project.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "occasion")
public class Occasion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "occasion", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    private Payload payload;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private IotDevice iotDevice;

    @Column(name = "occasion_type")
    private String occasionType;

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate = LocalDateTime.now();

    public Occasion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public IotDevice getIotDevice() {
        return iotDevice;
    }

    public void setIotDevice(IotDevice iotDevice) {
        this.iotDevice = iotDevice;
    }

    public String getOccasionType() {
        return occasionType;
    }

    public void setOccasionType(String occasionType) {
        this.occasionType = occasionType;
    }

    public LocalDateTime getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(LocalDateTime dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }
}
