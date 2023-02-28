package com.example.leaderIt_project.controllers;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersInIotDeviceException;
import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.dto.OccasionDTO;
import com.example.leaderIt_project.services.IotDeviceService;
import com.example.leaderIt_project.services.OccasionService;
import com.example.leaderIt_project.validators.IotDeviceValidator;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class IotDeviceController {

    private static final Logger logger = LogManager.getLogger(IotDeviceController.class);
    private final IotDeviceService iotDeviceService;

    private final OccasionService occasionService;

    private final IotDeviceValidator iotDeviceValidator;

    @Autowired
    public IotDeviceController(IotDeviceService iotDeviceService, OccasionService occasionService, IotDeviceValidator iotDeviceValidator) {
        this.iotDeviceService = iotDeviceService;
        this.occasionService = occasionService;
        this.iotDeviceValidator = iotDeviceValidator;
    }

    @GetMapping()
    public List<IotDeviceDTO> getAll(@RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "date_of_create", required = false) String dateOfCreate,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                     @RequestParam(value = "count", required = false, defaultValue = "10") String count) {
        return iotDeviceService.getAllDevices(type, dateOfCreate, page, count);
    }

    @GetMapping("/{serial_number}")
    public IotDeviceDTO getOneDeviceBySerialNumber(@PathVariable("serial_number") int serialNumber) {
        return iotDeviceService.getBySerialNumber(String.valueOf(serialNumber));
    }

    @GetMapping("/{serial_number}/occasions")
    public List<OccasionDTO> getAllOccasionsBySerialNumber(@PathVariable("serial_number") int serialNumber,
                                                           @RequestParam(value = "date_of_create", required = false) String dateOfCreate,
                                                           @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                           @RequestParam(value = "count", required = false, defaultValue = "10") String count) {
        return occasionService.getOccasionsBySerialNumber(String.valueOf(serialNumber), dateOfCreate, page, count);
    }

    @PostMapping()
    public String saveDevice(@RequestBody @Valid IotDeviceDTO iotDeviceDTO, BindingResult bindingResult) throws InvalidParametersInIotDeviceException {
        iotDeviceValidator.validate(iotDeviceDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage()).append("; ");
            }
            logger.warn("Failed to save new iot device, because: {}", stringBuilder.toString());
            throw new InvalidParametersInIotDeviceException(stringBuilder.toString());
        }
        return iotDeviceService.saveDevice(iotDeviceDTO);
    }

}