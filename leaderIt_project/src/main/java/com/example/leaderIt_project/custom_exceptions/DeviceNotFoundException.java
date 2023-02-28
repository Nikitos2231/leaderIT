package com.example.leaderIt_project.custom_exceptions;

public class DeviceNotFoundException extends Exception {

    public DeviceNotFoundException(String message) {
        super(message);
    }
}
