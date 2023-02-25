package com.example.leaderIt_project.custom_exceptions;

public class InvalidParametersForPayloadException extends Exception {

    public InvalidParametersForPayloadException(String message) {
        super(message);
    }
}
