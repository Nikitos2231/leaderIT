package com.example.leaderIt_project.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IotDeviceSerialNumberConverter implements AttributeConverter<String, Long> {

    @Override
    public Long convertToDatabaseColumn(String s) {
        return Long.parseLong(s);
    }

    @Override
    public String convertToEntityAttribute(Long aLong) {
        return String.valueOf(aLong);
    }
}
