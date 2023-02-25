package com.example.leaderIt_project.services;

import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.dto.OccasionDTO;
import com.example.leaderIt_project.key_workers.GeneratorKeys;
import com.example.leaderIt_project.key_workers.KeyWorker;
import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.models.Occasion;
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

    private final GeneratorKeys generatorKeys;

    private final KeyWorker keyWorker;

    @Autowired
    public IotDeviceService(IotDeviceRepository iotDeviceRepository, ModelMapper modelMapper, GeneratorKeys generatorKeys, KeyWorker keyWorker) {
        this.iotDeviceRepository = iotDeviceRepository;
        this.modelMapper = modelMapper;
        this.generatorKeys = generatorKeys;
        this.keyWorker = keyWorker;
    }

    public List<IotDeviceDTO> getAllDevices() {
        List<IotDeviceDTO> list = iotDeviceRepository.getAll().stream().map(this::convertToIotDeviceDto).collect(Collectors.toList());
//        list.forEach(d -> d.setSecreteKey(keyWorker.decryptKey(d.getSecreteKey())));
        return list;
    }

    public IotDeviceDTO getById(int id) {
        IotDeviceDTO iotDevice = convertToIotDeviceDto(iotDeviceRepository.getById(id));
//        iotDevice.setSecreteKey(keyWorker.decryptKey(iotDevice.getSecreteKey()));
        return iotDevice;
    }

    public IotDevice getIotDeviceBySerialNumber(String serialNumber) {
        return iotDeviceRepository.getIotDeviceBySerialNumber(serialNumber);
    }

    public String saveDevice(IotDeviceDTO iotDeviceDTO) {
        IotDevice iotDevice = convertToIotDevice(iotDeviceDTO);

        String generatedKey = generatorKeys.getGeneratedKey();
        String secreteKey = keyWorker.getEncryptKey(generatedKey);

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

}