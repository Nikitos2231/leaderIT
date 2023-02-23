package com.example.leaderIt_project.repositories;

import com.example.leaderIt_project.models.IotDevice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class IotDeviceRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public IotDeviceRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)

    public List<IotDevice> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select iot FROM IotDevice iot", IotDevice.class).getResultList();
    }

    @Transactional(readOnly = true)
    public IotDevice getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(IotDevice.class, id);
    }

    public void save(IotDevice iotDevice) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(iotDevice);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(getById(id));
    }
}