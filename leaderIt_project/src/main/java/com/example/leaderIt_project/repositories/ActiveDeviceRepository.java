package com.example.leaderIt_project.repositories;

import com.example.leaderIt_project.models.ActiveDevice;
import com.example.leaderIt_project.models.IotDevice;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ActiveDeviceRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ActiveDeviceRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(ActiveDevice activeDevice) {
        Session session = sessionFactory.getCurrentSession();

        session.persist(activeDevice);
    }

    @Transactional
    public void update(ActiveDevice activeDevice) {
        Session session = sessionFactory.getCurrentSession();

        session.update(activeDevice);
    }

    @Transactional(readOnly = true)
    public ActiveDevice getById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(ActiveDevice.class, id);
    }


    @Transactional
    public List<ActiveDevice> getAll() {
//        Session session = sessionFactory.getCurrentSession();

        return getActiveDevices();
    }

    @Transactional
    public List<ActiveDevice> getActiveDevices() {
        Session session = sessionFactory.getCurrentSession();

        List<ActiveDevice> activeDevices = session.createQuery("SELECT ad FROM ActiveDevice ad", ActiveDevice.class).getResultList();
        List<ActiveDevice> newList = new ArrayList<>();
        for (ActiveDevice activeDevice : activeDevices) {
            Duration duration = Duration.between(activeDevice.getDateLastActive(), LocalDateTime.now());
            int countMinutes = (int) (duration.getSeconds() / 60);
            if (countMinutes > 30) {
                session.remove(activeDevice);
            }
            else {
                newList.add(activeDevice);
            }
        }
        return newList;
    }
}
