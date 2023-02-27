package com.example.leaderIt_project.services;

import com.example.leaderIt_project.models.ActiveDevice;
import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.models.Occasion;
import com.example.leaderIt_project.repositories.ActiveDeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ActiveDeviceService {

    private final ModelMapper modelMapper;
    private final IotDeviceService iotDeviceService;
    private final ActiveDeviceRepository deviceActiveListRepository;

    @Autowired
    public ActiveDeviceService(ModelMapper modelMapper, IotDeviceService iotDeviceService, ActiveDeviceRepository deviceActiveListRepository) {
        this.modelMapper = modelMapper;
        this.iotDeviceService = iotDeviceService;
        this.deviceActiveListRepository = deviceActiveListRepository;
    }

    public List<ActiveDevice> getAllActiveDevices() {

        return deviceActiveListRepository.getAll();
    }

    public void save(IotDevice iotDevice) {
        ActiveDevice activeDevice = new ActiveDevice();

        ActiveDevice oldActiveDevice = deviceActiveListRepository.getById(iotDevice.getId());
        if (oldActiveDevice == null) {
            List<Occasion> occasions = iotDevice.getOccasions();
            Collections.sort(occasions, Comparator.comparing(Occasion::getDateOfCreate));

            activeDevice.setDateFirstActive(occasions.get(0).getDateOfCreate());
            activeDevice.setDateLastActive(LocalDateTime.now());

            activeDevice.setId(iotDevice.getId());

            deviceActiveListRepository.save(activeDevice);
        }
        else {
            oldActiveDevice.setDateLastActive(LocalDateTime.now());
            deviceActiveListRepository.update(oldActiveDevice);
        }
    }
}
