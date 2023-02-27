package com.example.leaderIt_project.repositories;

import com.example.leaderIt_project.models.IotDevice;
import com.example.leaderIt_project.models.Occasion;
import com.example.leaderIt_project.models.Payload;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class OccasionRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public OccasionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Occasion> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT o FROM Occasion o", Occasion.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Occasion getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Occasion.class, id);
    }

    public void save(Occasion occasion) {
        Session session = sessionFactory.getCurrentSession();
        IotDevice iotDevice = occasion.getIotDevice();

        List<Occasion> occasions = new ArrayList<>(iotDevice.getOccasions());
        occasions.add(occasion);
        iotDevice.setOccasions(occasions);

        session.update(iotDevice);
        session.persist(occasion);
        session.persist(occasion.getPayload());
    }

    @Transactional(readOnly = true)
    public List<Occasion> getAllBySerialNumber(int id, String dateOfCreate, String page, String count) {
        Session session = sessionFactory.getCurrentSession();

        Query<Occasion> query = session.createNativeQuery("SELECT * FROM occasion o where o.device_id = :id and to_char(o.date_of_create, 'YYYY-MM-DD') like :date", Occasion.class);

        query.setParameter("id", id);
        query.setParameter("date", dateOfCreate == null ? "%" : dateOfCreate);

        int pageSize = Integer.parseInt(count);
        int currentPage = Integer.parseInt(page);

        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);

        List<Occasion> occasions = query.getResultList();
        if (occasions != null && occasions.size() > 0) {
            occasions.get(0).getPayload().toString();
        }
        return occasions;
    }
}
