package com.example.leaderIt_project.converters;

import com.example.leaderIt_project.enums.IotDeviceTypeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IotDeviceTypeConverter implements AttributeConverter<String, IotDeviceTypeEnum> {

    @Override
    public IotDeviceTypeEnum convertToDatabaseColumn(String s) {
        return IotDeviceTypeEnum.valueOf(s);
    }

    @Override
    public String convertToEntityAttribute(IotDeviceTypeEnum iotDeviceTypeEnum) {
        return iotDeviceTypeEnum.name();
    }
}
