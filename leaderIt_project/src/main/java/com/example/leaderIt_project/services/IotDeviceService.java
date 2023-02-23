package com.example.leaderIt_project.services;

import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.repositories.IotDeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IotDeviceService {
    private final IotDeviceRepository iotDeviceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IotDeviceService(IotDeviceRepository iotDeviceRepository, ModelMapper modelMapper) {
        this.iotDeviceRepository = iotDeviceRepository;
        this.modelMapper = modelMapper;
    }

    public List<IotDeviceDTO> getAllDevices() {
        List<IotDeviceDTO> list = iotDeviceRepository.getAll().stream().map(this::convertToIotDeviceDto).collect(Collectors.toList());
        return list;
    }

    public IotDeviceDTO getById(int id) {
        IotDeviceDTO iotDevice = convertToIotDeviceDto(iotDeviceRepository.getById(id));
        return iotDevice;
    }

    public void saveDevice(IotDeviceDTO iotDeviceDTO) {
        IotDevice iotDevice = convertToIotDevice(iotDeviceDTO);
        iotDeviceRepository.save(iotDevice);
    }

    public void deleteDevice(int id) {
        iotDeviceRepository.delete(id);
    }

    private IotDeviceDTO convertToIotDeviceDto(IotDevice iotDevice) {
        return modelMapper.map(iotDevice, IotDeviceDTO.class);
    }

    private IotDevice convertToIotDevice(IotDeviceDTO iotDeviceDTO) {
        return modelMapper.map(iotDeviceDTO, IotDevice.class);
    }


}