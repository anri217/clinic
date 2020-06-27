package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.DaoErrorConstants;
import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.dao.exceptions.daoDoctors.DeleteDoctorException;
import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.dao.exceptions.daoPatients.DeletePatientException;
import com.haulmont.clinic.dao.hibernate.DaoPatients;
import com.haulmont.clinic.model.Patient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
}
