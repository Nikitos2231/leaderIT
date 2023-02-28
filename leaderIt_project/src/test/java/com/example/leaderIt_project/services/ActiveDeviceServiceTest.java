package com.example.leaderIt_project.services;

import com.example.leaderIt_project.models.ActiveDevice;
import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.models.MobilePayload;
import com.example.leaderIt_project.models.Occasion;
import com.example.leaderIt_project.repositories.ActiveDeviceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ActiveDeviceServiceTest {

    @Autowired
    private ActiveDeviceService activeDeviceService;

    @MockBean
    private ActiveDeviceRepository activeDeviceRepository;

    @Test
    void getAllActiveDevices() {
        ActiveDevice iotDevice = new ActiveDevice();
        ActiveDevice iotDevice1 = new ActiveDevice();
        List<ActiveDevice> iotDevices = List.of(iotDevice, iotDevice1);
        Mockito.doReturn(iotDevices).when(activeDeviceRepository).getAll();

        List<ActiveDevice> actual = activeDeviceService.getAllActiveDevices();

        Mockito.verify(activeDeviceRepository).getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(iotDevices, actual);
    }

    @Test
    void save_noOldActiveDevice() {
        ActiveDevice activeDevice = new ActiveDevice();
        Mockito.doReturn(activeDevice).when(activeDeviceRepository).getById(2);
        Mockito.doNothing().when(activeDeviceRepository).update(activeDevice);

        IotDevice iotDevice = new IotDevice();
        iotDevice.setId(2);
        Occasion occasion = new Occasion();
        occasion.setOccasionType("wer");
        MobilePayload mobilePayload = new MobilePayload();
        mobilePayload.setCountCallsInDay(123);
        mobilePayload.setOccasion(occasion);
        mobilePayload.setSecretKey("wert");
        occasion.setPayload(mobilePayload);
        occasion.setId(2);
        occasion.setDateOfCreate(LocalDateTime.now());

        List<Occasion> occasions = new java.util.ArrayList<>(List.of(occasion));

        iotDevice.setOccasions(occasions);

        activeDeviceService.save(iotDevice);

        Mockito.verify(activeDeviceRepository).update(activeDevice);
    }

    @Test
    void save_ExistsOldActiveDevice() {
        Mockito.doReturn(null).when(activeDeviceRepository).getById(2);
        Mockito.doNothing().when(activeDeviceRepository).save(Mockito.any());

        IotDevice iotDevice = new IotDevice();
        iotDevice.setId(2);
        Occasion occasion = new Occasion();
        occasion.setOccasionType("wer");
        MobilePayload mobilePayload = new MobilePayload();
        mobilePayload.setCountCallsInDay(123);
        mobilePayload.setOccasion(occasion);
        mobilePayload.setSecretKey("wert");
        occasion.setPayload(mobilePayload);
        occasion.setId(2);
        occasion.setDateOfCreate(LocalDateTime.now());

        List<Occasion> occasions = new java.util.ArrayList<>(List.of(occasion));

        iotDevice.setOccasions(occasions);

        activeDeviceService.save(iotDevice);

        Mockito.verify(activeDeviceRepository).save(Mockito.any());
    }
}