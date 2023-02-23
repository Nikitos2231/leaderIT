package com.example.leaderIt_project.listeners;

import com.example.leaderIt_project.models.IotDevice;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IotDeviceListener {

    private static final Logger logger = LogManager.getLogger(IotDeviceListener.class);

    @PrePersist
    private void prePersist(IotDevice iotDevice) {
        logger.info("Try to save new iot device: {}", iotDevice.toString());
    }

    @PostPersist
    private void postPersist(IotDevice iotDevice) {
        logger.info("New iot device was saved into db: {}",iotDevice.toString());
    }
}
