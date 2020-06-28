package com.haulmont.clinic.service.implementation;

import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.dao.exceptions.daoManager.ExecuteSqlStartScriptException;
import com.haulmont.clinic.dao.hibernate.DaoDoctors;
import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.implementation.DaoManagerImpl;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;

import java.util.List;

public class DoctorsServiceImpl implements DoctorsService {
    private static DoctorsServiceImpl instance;
    private DaoDoctors daoDoctors;

    private DoctorsServiceImpl() throws ExecuteSqlStartScriptException {
        DaoManager daoManager = DaoManagerImpl.getInstance();
        daoDoctors = daoManager.getDaoDoctors();
    }

    public static DoctorsServiceImpl getInstance() throws ExecuteSqlStartScriptException {
        if (instance == null){
            return new DoctorsServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Doctor> getAll() {
        return daoDoctors.getAll();
    }

    @Override
    public void create(Doctor doctor) throws CreateDoctorException {
        daoDoctors.create(doctor);
    }
}
