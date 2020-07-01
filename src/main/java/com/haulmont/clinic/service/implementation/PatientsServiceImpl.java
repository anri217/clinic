package com.haulmont.clinic.service.implementation;

import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.dao.exceptions.daoPatients.DeletePatientException;
import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.DaoPatients;
import com.haulmont.clinic.dao.hibernate.implementation.DaoManagerImpl;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.service.PatientsService;

import java.util.List;

public class PatientsServiceImpl implements PatientsService {

    private static PatientsServiceImpl instance;
    private DaoPatients daoPatients;

    private PatientsServiceImpl() {
        DaoManager daoManager = DaoManagerImpl.getInstance();
        daoPatients = daoManager.getDaoPatients();
    }

    public static PatientsServiceImpl getInstance() {
        if (instance == null){
            return new PatientsServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Patient> getAll() {
        return daoPatients.getAll();
    }

    @Override
    public void create(Patient patient) throws CreatePatientException {
        daoPatients.create(patient);
    }

    @Override
    public void update(Patient patient) {
        daoPatients.update(patient);
    }

    @Override
    public void delete(Patient patient) throws DeletePatientException {
        daoPatients.delete(patient);
    }
}
