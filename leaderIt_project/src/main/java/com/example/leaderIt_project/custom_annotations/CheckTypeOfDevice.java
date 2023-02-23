package com.example.leaderIt_project.custom_annotations;

import com.example.leaderIt_project.validators.TypeOfDeviceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.PARAMETER, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = TypeOfDeviceValidator.class)
@Documented
public @interface CheckTypeOfDevice {

    String message() default "{invalid value}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
