package com.example.leaderIt_project.controllers;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersInIotDeviceException;
import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.services.IotDeviceService;
import com.example.leaderIt_project.validators.IotDeviceValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class IotDeviceController {

    private final IotDeviceService iotDeviceService;

    private final IotDeviceValidator iotDeviceValidator;

    @Autowired
    public IotDeviceController(IotDeviceService iotDeviceService, IotDeviceValidator iotDeviceValidator) {
        this.iotDeviceService = iotDeviceService;
        this.iotDeviceValidator = iotDeviceValidator;
    }

    @GetMapping()
    public List<IotDeviceDTO> getAll() {
        return iotDeviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public IotDeviceDTO getOneDevice(@PathVariable("id") int id) {
        return iotDeviceService.getById(id);
    }

    @PostMapping()
    public String saveDevice(@RequestBody @Valid IotDeviceDTO iotDeviceDTO, BindingResult bindingResult) throws InvalidParametersInIotDeviceException {
        iotDeviceValidator.validate(iotDeviceDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage()).append("; ");
            }
            throw new InvalidParametersInIotDeviceException(stringBuilder.toString());
        }
        return iotDeviceService.saveDevice(iotDeviceDTO);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteDevice(@PathVariable("id") int id) {
        iotDeviceService.deleteDevice(id);
    }

}