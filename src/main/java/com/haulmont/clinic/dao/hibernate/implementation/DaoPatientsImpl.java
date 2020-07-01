package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.hibernate.DaoPatients;
import com.haulmont.clinic.model.Patient;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DaoPatientsImpl implements DaoPatients {
    private final SessionFactory sessionFactory;

    public DaoPatientsImpl(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public void create(Patient patient) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(patient);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Patient patient) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(patient);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Patient patient) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(patient);
        tx.commit();
        session.close();
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        Session session = sessionFactory.openSession();
        String hql = "From Patient";
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        for (Object o : query.list()) {
            patients.add((Patient) o);
        }
        tx1.commit();
        session.close();
        return patients;
    }
}
