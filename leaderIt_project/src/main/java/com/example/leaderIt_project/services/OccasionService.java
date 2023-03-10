package com.example.leaderIt_project.services;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersForPayloadException;
import com.example.leaderIt_project.dto.*;
import com.example.leaderIt_project.key_workers.KeyWorker;
import com.example.leaderIt_project.models.*;
import com.example.leaderIt_project.repositories.OccasionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OccasionService {

    private final OccasionRepository occasionRepository;
    private final IotDeviceService iotDeviceService;
    private final ModelMapper modelMapper;
    private final KeyWorker keyWorker;
    private final ActiveDeviceService activeListService;

    @Autowired
    public OccasionService(OccasionRepository occasionRepository, IotDeviceService iotDeviceService, ModelMapper modelMapper, KeyWorker keyWorker, ActiveDeviceService activeListService) {
        this.occasionRepository = occasionRepository;
        this.iotDeviceService = iotDeviceService;
        this.modelMapper = modelMapper;
        this.keyWorker = keyWorker;
        this.activeListService = activeListService;
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

    public boolean saveOccasion(OccasionDTO occasionDTO) throws InvalidParametersForPayloadException {
        Occasion occasion;
        try {
            occasion = convertToOccasion(occasionDTO);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            return false;
        }
        occasionRepository.save(occasion);
        activeListService.save(occasion.getIotDevice());
        return true;
    }

    private Occasion convertToOccasion(OccasionDTO occasionDTO) throws InvalidParametersForPayloadException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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

    private boolean isSecretKeyValid(PayloadDTO payloadDTO, String secretKeyFromDevice) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String secretKey = keyWorker.decryptKey(secretKeyFromDevice);
        String secreteKeyFromPayload = payloadDTO.getSecretKey();
        return secreteKeyFromPayload.equals(secretKey);
    }

    private Payload convertToPayload(PayloadDTO payloadDTO, int id) throws InvalidParametersForPayloadException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        IotDevice iotDevice = iotDeviceService.getOneById(id);
        String typeOfDevice = iotDevice.getDeviceType();

        String secreteKey = iotDevice.getSecreteKey();

        if (payloadDTO.getSecretKey() == null) {
            throw new InvalidParametersForPayloadException("Secret Key cannot be null");
        }

        try {
            if (!isSecretKeyValid(payloadDTO, secreteKey)) {
                throw new InvalidParametersForPayloadException("Secret Key is wrong!");
            }
        }
        catch (Exception e) {
            throw new InvalidParametersForPayloadException("Secret Key is wrong!");
        }

        payloadDTO.setSecretKey(keyWorker.encryptKey(secreteKey));

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

    public List<OccasionDTO> getOccasionsBySerialNumber(String serialNumber, String dateOfCreate, String page, String count) {
        IotDevice iotDevice = iotDeviceService.getIotDeviceBySerialNumber(serialNumber);

        if (iotDevice == null) {
            return new ArrayList<>();
        }

        int idOfDevice = iotDevice.getId();

        List<Occasion> occasions = getAllBySerialNumber(idOfDevice, dateOfCreate, page, count);
        return occasions.stream().map(this::convertToOccasionDTO).collect(Collectors.toList());
    }

    private List<Occasion> getAllBySerialNumber(int id, String dateOfCreate, String page, String count) {
        if (!iotDeviceService.isPaginationParametersValid(page, count)) {
            return new ArrayList<>();
        }
        return occasionRepository.getAllBySerialNumber(id, dateOfCreate, page, count);
    }

    public Map<String, Integer> getStatistics(String startDate, String endDate) {
        if (!isDatesValid(startDate, endDate)) {
            return new TreeMap<>();
        }

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter1);

        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter2);

        Map<String, Integer> statistics = new TreeMap<>();

        List<Occasion> occasions =  occasionRepository.getAll();
        for (Occasion occasion : occasions) {
            IotDevice iotDevice = occasion.getIotDevice();
            if (isDatesSuite(start, end, occasion)) {
                String typeOfDevice = iotDevice.getDeviceType();
                if (statistics.containsKey(typeOfDevice)) {
                    statistics.put(typeOfDevice, statistics.get(typeOfDevice) + 1);
                }
                else {
                    statistics.put(typeOfDevice, 1);
                }
            }
        }
        return statistics;
    }

    private boolean isDatesSuite(LocalDate startDate, LocalDate endDate, Occasion occasion) {
        LocalDate occasionDateOfCreate = occasion.getDateOfCreate().toLocalDate();
        return (occasionDateOfCreate.isEqual(startDate) || occasionDateOfCreate.isAfter(startDate)) &&
                (occasionDateOfCreate.isEqual(endDate) || occasionDateOfCreate.isBefore(endDate));
    }

    private boolean isDatesValid(String startDate, String endDate) {
        return Pattern.matches("^(\\d{4}-\\d{2}-\\d{2})$", startDate) &&
                Pattern.matches("^(\\d{4}-\\d{2}-\\d{2})$", endDate);
    }
}
