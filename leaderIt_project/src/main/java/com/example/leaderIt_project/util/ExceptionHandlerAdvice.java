package com.example.leaderIt_project.util;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersForOccasionException;
import com.example.leaderIt_project.custom_exceptions.InvalidParametersForPayloadException;
import com.example.leaderIt_project.custom_exceptions.InvalidParametersInIotDeviceException;
import com.example.leaderIt_project.error_responces.IotDeviceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(InvalidParametersInIotDeviceException.class)
    private ResponseEntity createError(InvalidParametersInIotDeviceException e) {
        IotDeviceResponse sensorErrorResponse = new IotDeviceResponse(new Date(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvalidParametersForPayloadException.class)
    private ResponseEntity createErrorForPayload(InvalidParametersForPayloadException e) {
        IotDeviceResponse sensorErrorResponse = new IotDeviceResponse(new Date(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvalidParametersForOccasionException.class)
    private ResponseEntity createErrorForOccasion(InvalidParametersForOccasionException e) {
        IotDeviceResponse sensorErrorResponse = new IotDeviceResponse(new Date(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST));
    }
}
