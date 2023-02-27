package com.example.leaderIt_project.services;

import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.key_workers.GeneratorKeys;
import com.example.leaderIt_project.key_workers.KeyWorker;
import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.repositories.IotDeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class IotDeviceService {
    private final IotDeviceRepository iotDeviceRepository;
    private final ModelMapper modelMapper;

    private final GeneratorKeys generatorKeys;

    private final KeyWorker keyWorker;

    @Autowired
    public IotDeviceService(IotDeviceRepository iotDeviceRepository, ModelMapper modelMapper, GeneratorKeys generatorKeys, KeyWorker keyWorker) {
        this.iotDeviceRepository = iotDeviceRepository;
        this.modelMapper = modelMapper;
        this.generatorKeys = generatorKeys;
        this.keyWorker = keyWorker;
    }

    public List<IotDeviceDTO> getAllDevices(String type, String dateOfCreate, String page, String count) {
        if (!isPaginationParametersValid(page, count)) {
            return new ArrayList<>();
        }
        List<IotDevice> devices = iotDeviceRepository.getAllByType(type, dateOfCreate, page, count);
        return devices == null ? new ArrayList<>() : devices.stream().map(this::convertToIotDeviceDto).collect(Collectors.toList());
    }

    public boolean isPaginationParametersValid(String pageSize, String currentPage) {
        return Pattern.matches("^(\\d+)$", pageSize) && Pattern.matches("^(\\d+)$", currentPage) && Integer.parseInt(pageSize) > 0;
    }

    public IotDeviceDTO getById(int id) {
        IotDeviceDTO iotDevice = convertToIotDeviceDto(iotDeviceRepository.getById(id));
        return iotDevice;
    }

    public IotDevice getIotDeviceBySerialNumber(String serialNumber) {
        return iotDeviceRepository.getIotDeviceBySerialNumber(serialNumber);
    }

    public String saveDevice(IotDeviceDTO iotDeviceDTO) {
        IotDevice iotDevice = convertToIotDevice(iotDeviceDTO);

        String generatedKey = generatorKeys.getGeneratedKey();
        String secreteKey;

        secreteKey = keyWorker.getEncryptKey(generatedKey);

        iotDevice.setSecreteKey(secreteKey);
        iotDeviceRepository.save(iotDevice);
        return generatedKey;
    }

    public void deleteDevice(int id) {
        iotDeviceRepository.delete(id);
    }

    public IotDevice getOneById(int id) {
        return iotDeviceRepository.getOneById(id);
    }

    private IotDeviceDTO convertToIotDeviceDto(IotDevice iotDevice) {
        return modelMapper.map(iotDevice, IotDeviceDTO.class);
    }

    private IotDevice convertToIotDevice(IotDeviceDTO iotDeviceDTO) {
        return modelMapper.map(iotDeviceDTO, IotDevice.class);
    }

    public IotDeviceDTO getBySerialNumber(String serialNumber) {
        IotDevice iotDevice = iotDeviceRepository.getIotDeviceBySerialNumber(serialNumber);
        return iotDevice == null ? null : modelMapper.map(iotDevice, IotDeviceDTO.class);
    }
}