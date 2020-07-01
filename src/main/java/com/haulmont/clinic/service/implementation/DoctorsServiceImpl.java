package com.haulmont.clinic.service.implementation;

import com.haulmont.clinic.dao.hibernate.DaoDoctors;
import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.implementation.DaoManagerImpl;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;

import java.util.List;

public class DoctorsServiceImpl implements DoctorsService {
    private static DoctorsServiceImpl instance;
    private DaoDoctors daoDoctors;

    private DoctorsServiceImpl() {
        DaoManager daoManager = DaoManagerImpl.getInstance();
        daoDoctors = daoManager.getDaoDoctors();
    }

    public static DoctorsServiceImpl getInstance() {
        if (instance == null){
            instance = new DoctorsServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Doctor> getAll() {
        return daoDoctors.getAll();
    }

    @Override
    public void create(Doctor doctor) {
        daoDoctors.create(doctor);
    }

    @Override
    public void delete(Doctor doctor) {
        daoDoctors.delete(doctor);
    }

    @Override
    public void update(Doctor doctor) {
        daoDoctors.update(doctor);
    }
}
