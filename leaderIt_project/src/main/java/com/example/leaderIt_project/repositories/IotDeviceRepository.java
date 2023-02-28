package com.example.leaderIt_project.repositories;

import com.example.leaderIt_project.models.IotDevice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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

    @Transactional(readOnly = true)
    public IotDevice getIotDeviceBySerialNumber(String serialNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query<IotDevice> query = session.createQuery("SELECT iot FROM IotDevice iot where iot.serialNumber = :serial", IotDevice.class);
        query.setParameter("serial", serialNumber);
        List<IotDevice> iotDevices = query.list();
        return iotDevices.size() == 0 ? null : iotDevices.get(0);
    }

    @Transactional(readOnly = true)
    public IotDevice getOneById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(IotDevice.class, id);
    }

    @Transactional(readOnly = true)
    public List<IotDevice> getAllByType(String type, String dateOfCreate, String page, String count) {
        Session session = sessionFactory.getCurrentSession();
        Query<IotDevice> query = session.createNativeQuery("SELECT * FROM iot_device d where d.device_type like :type and to_char(d.date_of_create, 'YYYY-MM-DD') like :dateOfCreate", IotDevice.class);

        query.setParameter("type", type == null ? "%" : type);
        query.setParameter("dateOfCreate", dateOfCreate == null ? "%" : dateOfCreate);

        int pageSize = Integer.parseInt(count);
        int currentPage = Integer.parseInt(page);

        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);

        List<IotDevice> iotDevices = query.list();

        if (iotDevices == null) {
            return null;
        }

        return iotDevices.size() == 0 ? null : iotDevices;
    }
}