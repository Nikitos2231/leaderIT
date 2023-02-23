package com.example.leaderIt_project.models;

import com.example.leaderIt_project.converters.IotDeviceSerialNumberConverter;
import com.example.leaderIt_project.converters.IotDeviceTypeConverter;
import com.example.leaderIt_project.enums.IotDeviceTypeEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "iot_device")

public class IotDevice {

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

    @Column(name = "secrete_key")
    private String secreteKey;

    @Column(name = "date_of_create")
    @Temporal(TemporalType.DATE)
    private Date dateOfCreate = new Date();

    public IotDevice() {

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
}

