package com.example.leaderIt_project.services;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersForPayloadException;
import com.example.leaderIt_project.dto.*;
import com.example.leaderIt_project.models.*;
import com.example.leaderIt_project.repositories.OccasionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OccasionService {

    private final OccasionRepository occasionRepository;
    private final IotDeviceService iotDeviceService;
    private final ModelMapper modelMapper;

    @Autowired
    public OccasionService(OccasionRepository occasionRepository, IotDeviceService iotDeviceService, ModelMapper modelMapper) {
        this.occasionRepository = occasionRepository;
        this.iotDeviceService = iotDeviceService;
        this.modelMapper = modelMapper;
    }

    public List<OccasionDTO> getAllOccasions() {
        return occasionRepository.getAll()
                .stream().map(this::convertToOccasionDTO).collect(Collectors.toList());
    }

    public OccasionDTO getOne(int id) {
        return convertToOccasionDTO(occasionRepository.getById(id));
    }

    private OccasionDTO convertToOccasionDTO(Occasion occasion) {
        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setOccasionType(occasion.getOccasionType());
        occasionDTO.setDeviceID(occasion.getIotDevice().getId());

        occasionDTO.setPayloadDTO(modelMapper.map(occasion.getPayload(), PayloadDTO.class));

        return occasionDTO;
    }

    private FridgePayloadDTO convertToFridgePayloadDTO(Payload payload) {
        return modelMapper.map(payload, FridgePayloadDTO.class);
    }

    private MobilePayloadDTO convertToMobilePayloadDTO(Payload payload) {
        return modelMapper.map(payload, MobilePayloadDTO.class);
    }

    private WatchPaylaodDTO convertToWatchPayloadDTO(Payload payload) {
        return modelMapper.map(payload, WatchPaylaodDTO.class);
    }

    public void saveOccasion(OccasionDTO occasionDTO) throws InvalidParametersForPayloadException {
        Occasion occasion = convertToOccasion(occasionDTO);
        occasionRepository.save(occasion);
    }

    private Occasion convertToOccasion(OccasionDTO occasionDTO) throws InvalidParametersForPayloadException {
        Occasion occasion = new Occasion();
        occasion.setOccasionType(occasionDTO.getOccasionType());

        occasion.setIotDevice(modelMapper.map(iotDeviceService.getById(occasionDTO.getDeviceID()), IotDevice.class));

        IotDevice iotDevice = iotDeviceService.getOneById(occasionDTO.getDeviceID());
        occasion.setIotDevice(iotDevice);

        Payload payload = convertToPayload(occasionDTO.getPayloadDTO(), occasionDTO.getDeviceID());
        payload.setOccasion(occasion);
        occasion.setPayload(payload);

        return occasion;
    }

    private boolean isSecretKeyValid(PayloadDTO payloadDTO, String secretKeyFromDevice) {
        String secreteKeyFromPayload = payloadDTO.getSecretKey();
        return secreteKeyFromPayload.equals(secretKeyFromDevice);
    }

    private Payload convertToPayload(PayloadDTO payloadDTO, int id) throws InvalidParametersForPayloadException {

        IotDevice iotDevice = iotDeviceService.getOneById(id);
        String typeOfDevice = iotDevice.getDeviceType();

        String secreteKey = iotDevice.getSecreteKey();

        if (payloadDTO.getSecretKey() == null) {
            throw new InvalidParametersForPayloadException("Secret Key cannot be null");
        }

        if (!isSecretKeyValid(payloadDTO, secreteKey)) {
            throw new InvalidParametersForPayloadException("Secret Key is wrong!");
        }

        if (typeOfDevice.equals("SMART_MOBILE")) {
            if (isMobilePayload(payloadDTO)) {
                return modelMapper.map(payloadDTO, MobilePayload.class);
            }
            throw new InvalidParametersForPayloadException("Invalid parameters for payload. Device with id: "
                    + id + " is " + typeOfDevice
                    + ". You may transmit the follow parameters for this type of device: "
                    + "{secret_key: <key for device>, countCallsInDay: <Count calls>}");
        }

        else if (typeOfDevice.equals("SMART_FRIDGE")) {
            if (isFridgePayload(payloadDTO)) {
                return modelMapper.map(payloadDTO, FridgePayload.class);
            }
            throw new InvalidParametersForPayloadException("Invalid parameters for payload. Device with id: "
                    + id + " is " + typeOfDevice
                    + ". You may transmit the follow parameters for this type of device: "
                    + "{secret_key: <key for device>, dailyEnergyConsumption: <Energy consumption>}");
        }
        else if (typeOfDevice.equals("SMART_WATCH")) {
            if (isWatchPayload(payloadDTO)) {
                return modelMapper.map(payloadDTO, WatchPayload.class);
            }
            throw new InvalidParametersForPayloadException("Invalid parameters for payload. Device with id: "
                    + id + " is " + typeOfDevice
                    + ". You may transmit the follow parameters for this type of device: "
                    + "{secret_key: <key for device>, countStepsInDay: <Count steps>}");
        }

        throw new InvalidParametersForPayloadException("Invalid parameters for payload!");
    }

    private boolean isMobilePayload(PayloadDTO payloadDTO) {
        return payloadDTO.getCountCallsInDay() != null &&
                payloadDTO.getCountStepsInDay() == null &&
                payloadDTO.getDailyEnergyConsumption() == null;
    }

    private boolean isWatchPayload(PayloadDTO payloadDTO) {
        return payloadDTO.getCountCallsInDay() == null &&
                payloadDTO.getCountStepsInDay() != null &&
                payloadDTO.getDailyEnergyConsumption() == null;
    }

    private boolean isFridgePayload(PayloadDTO payloadDTO) {
        return payloadDTO.getCountCallsInDay() == null &&
                payloadDTO.getCountStepsInDay() == null &&
                payloadDTO.getDailyEnergyConsumption() != null;
    }
}
