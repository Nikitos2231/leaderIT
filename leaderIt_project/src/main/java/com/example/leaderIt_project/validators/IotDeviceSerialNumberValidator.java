package com.example.leaderIt_project.validators;

import com.example.leaderIt_project.custom_annotations.CheckSerialNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class IotDeviceSerialNumberValidator implements ConstraintValidator<CheckSerialNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && Pattern.matches("^(\\d{10})$", s);
    }

}
