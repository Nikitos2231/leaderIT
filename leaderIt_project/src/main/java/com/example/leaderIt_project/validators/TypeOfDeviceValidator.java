package com.example.leaderIt_project.validators;

import com.example.leaderIt_project.custom_annotations.CheckTypeOfDevice;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class TypeOfDeviceValidator implements ConstraintValidator<CheckTypeOfDevice, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && (s.equals("SMART_WATCH") || s.equals("SMART_FRIDGE") || s.equals("SMART_MOBILE"));
    }
}
