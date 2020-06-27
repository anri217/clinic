package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.DaoErrorConstants;
import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.dao.exceptions.daoDoctors.DeleteDoctorException;
import com.haulmont.clinic.dao.hibernate.DaoDoctors;
import com.haulmont.clinic.model.Doctor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DaoDoctorsImpl implements DaoDoctors {
    private final SessionFactory sessionFactory;

    public DaoDoctorsImpl(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public void create(Doctor doctor) throws CreateDoctorException {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(doctor);
            tx.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new CreateDoctorException(DaoErrorConstants.CREATE_DOCTOR_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void delete(Doctor doctor) throws DeleteDoctorException {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(doctor);
            tx.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new DeleteDoctorException(DaoErrorConstants.DELETE_DOCTOR_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
