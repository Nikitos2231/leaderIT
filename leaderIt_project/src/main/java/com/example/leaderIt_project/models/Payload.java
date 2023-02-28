package com.example.leaderIt_project.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "payload")
@Inheritance(strategy = InheritanceType.JOINED)
public class Payload implements Serializable {

    @Id
    @Column(name = "id")
    protected int id;
    @Column(name = "secret_key")
    protected String secretKey;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    protected Occasion occasion;

    public Payload() {
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
