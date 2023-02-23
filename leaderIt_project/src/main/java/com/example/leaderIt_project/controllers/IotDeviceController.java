package com.example.leaderIt_project.controllers;

import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.services.IotDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class IotDeviceController {

    private final IotDeviceService iotDeviceService;

    @Autowired
    public IotDeviceController(IotDeviceService iotDeviceService) {
        this.iotDeviceService = iotDeviceService;
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
    public String saveDevice(@RequestBody IotDeviceDTO iotDeviceDTO) {
        return iotDeviceService.saveDevice(iotDeviceDTO);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteDevice(@PathVariable("id") int id) {
        iotDeviceService.deleteDevice(id);
    }

}