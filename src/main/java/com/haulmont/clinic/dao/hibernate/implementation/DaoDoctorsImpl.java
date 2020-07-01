package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.hibernate.DaoDoctors;
import com.haulmont.clinic.model.Doctor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DaoDoctorsImpl implements DaoDoctors {
    private final SessionFactory sessionFactory;

    public DaoDoctorsImpl(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public void create(Doctor doctor) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(doctor);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Doctor doctor) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(doctor);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Doctor doctor){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(doctor);
        tx.commit();
        session.close();
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        Session session = sessionFactory.openSession();
        String hql = "From Doctor";
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        for (Object o : query.list()) {
            doctors.add((Doctor) o);
        }
        tx1.commit();
        session.close();
        return doctors;
    }
}
