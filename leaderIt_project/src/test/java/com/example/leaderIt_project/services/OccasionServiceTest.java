package com.example.leaderIt_project.services;

import com.example.leaderIt_project.custom_exceptions.InvalidParametersForPayloadException;
import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.dto.OccasionDTO;
import com.example.leaderIt_project.dto.PayloadDTO;
import com.example.leaderIt_project.key_workers.KeyWorker;
import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.models.MobilePayload;
import com.example.leaderIt_project.models.Occasion;
import com.example.leaderIt_project.models.Payload;
import com.example.leaderIt_project.repositories.OccasionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OccasionServiceTest {

    @Autowired
    private OccasionService occasionService;

    @MockBean
    private OccasionRepository occasionRepository;
    @MockBean
    private IotDeviceService iotDeviceService;
    @MockBean
    private KeyWorker keyWorker;
    @MockBean
    private ActiveDeviceService activeListService;

    @Test
    void getAllOccasions() {
        Occasion occasion1 = new Occasion();
        occasion1.setId(1);
        occasion1.setOccasionType("SOMETHING");
        occasion1.setIotDevice(new IotDevice());
        occasion1.setPayload(new MobilePayload());
        Occasion occasion2 = new Occasion();
        occasion2.setId(2);
        occasion2.setOccasionType("SOMETHING2");
        occasion2.setIotDevice(new IotDevice());
        occasion2.setPayload(new MobilePayload());
        List<Occasion> occasionList = new ArrayList<>();
        occasionList.add(occasion1);
        occasionList.add(occasion2);
        Mockito.doReturn(occasionList).when(occasionRepository).getAll();

        List<OccasionDTO> actual = occasionService.getAllOccasions();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(actual.get(0).getOccasionType(), occasion1.getOccasionType());
        assertEquals(actual.get(1).getOccasionType(), occasion2.getOccasionType());
        Mockito.verify(occasionRepository).getAll();
    }

    @Test
    void getOne() {
        Occasion occasion1 = new Occasion();
        occasion1.setId(1);
        occasion1.setOccasionType("SOMETHING");
        occasion1.setIotDevice(new IotDevice());
        occasion1.setPayload(new MobilePayload());
        Mockito.doReturn(occasion1).when(occasionRepository).getById(1);

        OccasionDTO actual = occasionService.getOne(1);

        assertNotNull(actual);
        assertEquals(actual.getOccasionType(), occasion1.getOccasionType());
        Mockito.verify(occasionRepository).getById(1);
    }


    @Test
    void saveOccasion_SMART_MOBILE_TYPE_SUCCESS() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_MOBILE");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setCountCallsInDay("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertDoesNotThrow(() -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertTrue(actual.get());
    }

    @Test
    void saveOccasion_SMART_MOBILE_TYPE_FAILED() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_MOBILE");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setCountStepsInDay("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertThrows(InvalidParametersForPayloadException.class, () -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertFalse(actual.get());
    }

    @Test
    void saveOccasion_SMART_FRIDGE_TYPE_SUCCESS() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_FRIDGE");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setDailyEnergyConsumption("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertDoesNotThrow(() -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertTrue(actual.get());
    }

    @Test
    void saveOccasion_SMART_FRIDGE_TYPE_FAILED() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_FRIDGE");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setCountStepsInDay("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertThrows(InvalidParametersForPayloadException.class, () -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertFalse(actual.get());
    }

    @Test
    void saveOccasion_SMART_WATCH_TYPE_SUCCESS() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_WATCH");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setCountStepsInDay("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertDoesNotThrow(() -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertTrue(actual.get());
    }

    @Test
    void saveOccasion_SMART_WATCH_TYPE_FAILED() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_WATCH");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setDailyEnergyConsumption("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertThrows(InvalidParametersForPayloadException.class, () -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertFalse(actual.get());
    }

    @Test
    void saveOccasion_SecretKeyIsNull() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_WATCH");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setDailyEnergyConsumption("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertThrows(InvalidParametersForPayloadException.class, () -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertFalse(actual.get());
    }

    @Test
    void saveOccasion_WrongSecretKey() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_WATCH");
        iotDevice.setSecreteKey("$KEY!@#$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY!@#$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setDailyEnergyConsumption("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertThrows(InvalidParametersForPayloadException.class, () -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertFalse(actual.get());
    }

    @Test
    void saveOccasion_DoestExistingType() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_CAR");
        iotDevice.setSecreteKey("$KEY$");
        Mockito.doReturn(iotDeviceDTO).when(iotDeviceService).getById(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getOneById(1);
        Mockito.doReturn("$KEY$").when(keyWorker).decryptKey(Mockito.anyString());

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setSecretKey("$KEY$");
        payloadDTO.setCountStepsInDay("123");

        OccasionDTO occasionDTO = new OccasionDTO();
        occasionDTO.setDeviceID(1);
        occasionDTO.setPayloadDTO(payloadDTO);

        AtomicBoolean actual = new AtomicBoolean(false);
        Assertions.assertThrows(InvalidParametersForPayloadException.class, () -> actual.set(occasionService.saveOccasion(occasionDTO)));

        assertFalse(actual.get());
    }

    @Test
    void getOccasionsBySerialNumber_dontFoundIotDevice() {
        Mockito.doReturn(null).when(iotDeviceService).getIotDeviceBySerialNumber(Mockito.anyString());
        List<OccasionDTO> actual = occasionService.getOccasionsBySerialNumber("", "", "", "");
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void getOccasionsBySerialNumber_InvalidPaginationParameters() {
        IotDevice iotDevice = new IotDevice();
        iotDevice.setId(1);
        Mockito.doReturn(iotDevice).when(iotDeviceService).getIotDeviceBySerialNumber(Mockito.anyString());

        List<OccasionDTO> actual = occasionService.getOccasionsBySerialNumber("", "", "", "");

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void getOccasionsBySerialNumber_Success() {
        IotDevice iotDevice = new IotDevice();
        iotDevice.setId(1);

        Payload payload = new MobilePayload();
        payload.setSecretKey("123");

        Occasion occasion = new Occasion();
        occasion.setPayload(payload);
        occasion.setIotDevice(iotDevice);

        List<Occasion> occasions = new ArrayList<>(List.of(occasion));

        Mockito.doReturn(iotDevice).when(iotDeviceService).getIotDeviceBySerialNumber(Mockito.anyString());
        Mockito.doReturn(occasions).when(occasionRepository).getAllBySerialNumber(1, "", "2", "3");
        Mockito.doReturn(true).when(iotDeviceService).isPaginationParametersValid(Mockito.anyString(), Mockito.anyString());

        List<OccasionDTO> actual = occasionService.getOccasionsBySerialNumber("", "", "2", "3");

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void getStatistics_DateIsNotValid() {

        Map<String, Integer> actual1 = occasionService.getStatistics("2002-12-999999", "2002-04-19");
        Map<String, Integer> actual2 = occasionService.getStatistics("2002-12-12", "2002-04-19999");

        assertNotNull(actual1);
        assertNotNull(actual2);
    }


    @Test
    void getStatistics_Success() {
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceType("SMART_FRIDGE");

        Occasion occasion1 = new Occasion();
        occasion1.setIotDevice(iotDevice);
        occasion1.setDateOfCreate(LocalDateTime.of(2002, 2, 4, 23, 23));
        Occasion occasion2 = new Occasion();
        occasion2.setDateOfCreate(LocalDateTime.of(2004, 2, 4, 23, 23));
        occasion2.setIotDevice(iotDevice);
        Occasion occasion3 = new Occasion();
        occasion3.setDateOfCreate(LocalDateTime.of(2002, 3, 4, 23, 23));
        occasion3.setIotDevice(iotDevice);
        List<Occasion> occasions = new ArrayList<>(List.of(occasion1, occasion2, occasion3));
        Mockito.doReturn(occasions).when(occasionRepository).getAll();

        Map<String, Integer> actual = occasionService.getStatistics("2002-01-12", "2002-04-19");

        assertNotNull(actual);
        assertEquals(actual.size(), 1);
        Mockito.verify(occasionRepository).getAll();
    }
}