package com.example.leaderIt_project.validators;

import com.example.leaderIt_project.dto.OccasionDTO;
import com.example.leaderIt_project.services.IotDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OccasionValidator implements Validator {

    private final IotDeviceService iotDeviceService;

    @Autowired
    public OccasionValidator(IotDeviceService iotDeviceService) {
        this.iotDeviceService = iotDeviceService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OccasionDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OccasionDTO occasionDTO = (OccasionDTO) target;

        if (iotDeviceService.getOneById(occasionDTO.getDeviceID()) == null) {
            errors.rejectValue("deviceID", "", "Device with this id doesn't exist");
        }
    }
}
