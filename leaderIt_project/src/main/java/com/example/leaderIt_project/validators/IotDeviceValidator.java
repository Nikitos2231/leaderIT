package com.example.leaderIt_project.validators;

import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.services.IotDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class IotDeviceValidator implements Validator {

    private final IotDeviceService iotDeviceService;

    @Autowired
    public IotDeviceValidator(IotDeviceService iotDeviceService) {
        this.iotDeviceService = iotDeviceService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IotDeviceDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IotDeviceDTO iotDeviceDTO = (IotDeviceDTO) target;

        if (iotDeviceDTO.getSerialNumber() == null || !Pattern.matches("^(\\d{10})$", iotDeviceDTO.getSerialNumber())) {
            errors.rejectValue("serialNumber", "", "serial number should have size = 10, and only consist of figures");
            return;
        }

        if (iotDeviceService.getIotDeviceBySerialNumber(iotDeviceDTO.getSerialNumber()) != null) {
            errors.rejectValue("serialNumber", "", "Iot device with this serial number already exists");
        }

    }
}
