package com.example.leaderIt_project.models;

import com.example.leaderIt_project.converters.IotDeviceSerialNumberConverter;
import com.example.leaderIt_project.converters.IotDeviceTypeConverter;
import com.example.leaderIt_project.listeners.IotDeviceListener;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "iot_device")
@EntityListeners(IotDeviceListener.class)
public class IotDevice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "serial_number")
    @Convert(converter = IotDeviceSerialNumberConverter.class)
    private String serialNumber;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_type")
    @Convert(converter = IotDeviceTypeConverter.class)
    private String deviceType;

    @Column(name = "secret_key")
    private String secreteKey;

    @Column(name = "date_of_create")
    @Temporal(TemporalType.DATE)
    private Date dateOfCreate = new Date();

    @OneToMany(mappedBy = "iotDevice", fetch = FetchType.EAGER)
    private List<Occasion> occasions;

    public IotDevice() {

    }

    public List<Occasion> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<Occasion> occasions) {
        this.occasions = occasions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSecreteKey() {
        return secreteKey;
    }

    public void setSecreteKey(String secreteKey) {
        this.secreteKey = secreteKey;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    @Override
    public String toString() {
        return "IotDevice{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", secreteKey='" + secreteKey + '\'' +
                ", dateOfCreate=" + dateOfCreate +
                '}';
    }
}

