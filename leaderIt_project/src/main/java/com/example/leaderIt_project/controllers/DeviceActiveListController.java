package com.example.leaderIt_project.controllers;

import com.example.leaderIt_project.models.ActiveDevice;
import com.example.leaderIt_project.services.ActiveDeviceService;
import com.example.leaderIt_project.services.IotDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/active_devices")
public class DeviceActiveListController {

    private final ActiveDeviceService deviceActiveListService;

    @Autowired
    public DeviceActiveListController(ActiveDeviceService deviceActiveListService) {
        this.deviceActiveListService = deviceActiveListService;
    }

    @GetMapping()
    public List<ActiveDevice> getActiveDevices() {
        return deviceActiveListService.getAllActiveDevices();
    }
}
