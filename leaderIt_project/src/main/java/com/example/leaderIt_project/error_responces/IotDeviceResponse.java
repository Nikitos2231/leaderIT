package com.example.leaderIt_project.error_responces;

import java.util.Date;

public class IotDeviceResponse {

    private Date timestamp;
    private String message;

    public IotDeviceResponse(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public IotDeviceResponse() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
