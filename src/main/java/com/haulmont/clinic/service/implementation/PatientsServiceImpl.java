package com.haulmont.clinic.service.implementation;

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
            instance = new PatientsServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Patient> getAll() {
        return daoPatients.getAll();
    }

    @Override
    public void create(Patient patient) {
        daoPatients.create(patient);
    }

    @Override
    public void update(Patient patient) {
        daoPatients.update(patient);
    }

    @Override
    public void delete(Patient patient) {
        daoPatients.delete(patient);
    }
}
