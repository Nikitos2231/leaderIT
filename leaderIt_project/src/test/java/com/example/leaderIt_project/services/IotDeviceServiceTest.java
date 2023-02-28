package com.example.leaderIt_project.services;

import com.example.leaderIt_project.dto.IotDeviceDTO;
import com.example.leaderIt_project.key_workers.GeneratorKeys;
import com.example.leaderIt_project.key_workers.KeyWorker;
import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.repositories.IotDeviceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class IotDeviceServiceTest {

    @Autowired
    private IotDeviceService iotDeviceService;

    @MockBean
    private IotDeviceRepository iotDeviceRepository;
    @MockBean
    private GeneratorKeys generatorKeys;
    @MockBean
    private KeyWorker keyWorker;

    @Test
    void getAllDevices_RightParameters() {
        IotDevice iotDevice1 = new IotDevice();
        iotDevice1.setId(1);
        iotDevice1.setSerialNumber("123");
        IotDevice iotDevice2 = new IotDevice();
        iotDevice2.setId(2);
        iotDevice2.setSerialNumber("321");
        List<IotDevice> iotDeviceList = new ArrayList<>();
        iotDeviceList.add(iotDevice1);
        iotDeviceList.add(iotDevice2);
        Mockito.doReturn(iotDeviceList).when(iotDeviceRepository).getAllByType("", "", "2", "3");

        List<IotDeviceDTO> actual = iotDeviceService.getAllDevices("", "", "2", "3");

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Mockito.verify(iotDeviceRepository).getAllByType("", "", "2", "3");
    }

    @Test
    void getAllDevices_InvalidParametersParameters() {
        List<IotDevice> iotDeviceList = new ArrayList<>();
        Mockito.doReturn(iotDeviceList).when(iotDeviceRepository).getAllByType("", "", "-123", "-323");

        List<IotDeviceDTO> actual = iotDeviceService.getAllDevices("", "", "-123", "-323");

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(iotDeviceRepository, Mockito.times(0))
                .getAllByType("", "", "-123", "-323");
    }

    @Test
    void isPaginationParametersValid_ValidParameters() {
        boolean actual1 = iotDeviceService.isPaginationParametersValid("1", "32");
        boolean actual2 = iotDeviceService.isPaginationParametersValid("2", "1");
        boolean actual3 = iotDeviceService.isPaginationParametersValid("1", "0");
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    void isPaginationParametersValid_InvalidParameters() {
        boolean actual1 = iotDeviceService.isPaginationParametersValid("0", "3");
        boolean actual2 = iotDeviceService.isPaginationParametersValid("wer", "wer");
        boolean actual3= iotDeviceService.isPaginationParametersValid("2", "wer");
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
    }

    @Test
    void getById() {
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDeviceName("device_1");
        iotDevice.setDeviceType("SMART_MOBILE");
        iotDevice.setSerialNumber("123");
        Mockito.doReturn(iotDevice).when(iotDeviceRepository).getById(2);

        IotDeviceDTO iotDeviceDTO = iotDeviceService.getById(2);

        assertNotNull(iotDeviceDTO);
        assertEquals(iotDeviceDTO.getDeviceName(), iotDevice.getDeviceName());
        assertEquals(iotDeviceDTO.getDeviceType(), iotDevice.getDeviceType());
        assertEquals(iotDeviceDTO.getSerialNumber(), iotDevice.getSerialNumber());
        Mockito.verify(iotDeviceRepository).getById(2);
    }

    @Test
    void getIotDeviceBySerialNumber() {
        IotDevice iotDevice = new IotDevice();
        iotDevice.setId(2);
        iotDevice.setSerialNumber("123");
        Mockito.doReturn(iotDevice).when(iotDeviceRepository).getIotDeviceBySerialNumber("123");

        IotDevice actual = iotDeviceService.getIotDeviceBySerialNumber("123");

        assertNotNull(actual);
        assertEquals(actual.getId(), iotDevice.getId());
        assertEquals(actual.getSerialNumber(), iotDevice.getSerialNumber());
        Mockito.verify(iotDeviceRepository).getIotDeviceBySerialNumber("123");
    }

    @Test
    void saveDevice() {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        iotDeviceDTO.setDeviceName("device_1");
        iotDeviceDTO.setDeviceType("SMART_FRIDGE");
        iotDeviceDTO.setSerialNumber("123");

        Mockito.doReturn("SECRET_KEY").when(generatorKeys).getGeneratedKey();
        Mockito.doReturn("$ENCRYPTED_KEY$").when(keyWorker).getEncryptKey("SECRET_KEY");
        Mockito.doNothing().when(iotDeviceRepository).save(Mockito.any());

        String actual = iotDeviceService.saveDevice(iotDeviceDTO);

        assertNotNull(actual);
        assertEquals(actual, "SECRET_KEY");
        Mockito.verify(generatorKeys).getGeneratedKey();
        Mockito.verify(keyWorker).getEncryptKey("SECRET_KEY");
    }

    @Test
    void getOneById() {
        IotDevice iotDevice = new IotDevice();
        iotDevice.setId(1);
        Mockito.doReturn(iotDevice).when(iotDeviceRepository).getOneById(1);

        IotDevice actual = iotDeviceService.getOneById(1);

        assertNotNull(actual);
        assertEquals(actual.getId(), iotDevice.getId());
        Mockito.verify(iotDeviceRepository).getOneById(1);
    }

    @Test
    void getBySerialNumber() {
        IotDevice iotDevice = new IotDevice();
        iotDevice.setId(1);
        iotDevice.setSerialNumber("123");
        Mockito.doReturn(iotDevice).when(iotDeviceRepository).getIotDeviceBySerialNumber("123");

        IotDeviceDTO actual = iotDeviceService.getBySerialNumber("123");

        assertNotNull(actual);
        assertEquals(actual.getSerialNumber(), iotDevice.getSerialNumber());
        Mockito.verify(iotDeviceRepository).getIotDeviceBySerialNumber("123");
    }
}