package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.DaoErrorConstants;
import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.dao.exceptions.daoDoctors.DeleteDoctorException;
import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.dao.exceptions.daoPatients.DeletePatientException;
import com.haulmont.clinic.dao.hibernate.DaoPatients;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.model.Patient;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class DaoPatientsImpl implements DaoPatients {
    private final SessionFactory sessionFactory;

    public DaoPatientsImpl(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public void create(Patient patient) throws CreatePatientException {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(patient);
            tx.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new CreatePatientException(DaoErrorConstants.CREATE_PATIENT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void delete(Patient patient) throws DeletePatientException {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(patient);
            tx.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new DeletePatientException(DaoErrorConstants.DELETE_PATIENT_EXCEPTION_MESSAGE + e.getMessage());
        }
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
