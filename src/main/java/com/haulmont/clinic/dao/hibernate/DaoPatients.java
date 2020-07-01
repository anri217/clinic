package com.haulmont.clinic.dao.hibernate;

import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.dao.exceptions.daoPatients.DeletePatientException;
import com.haulmont.clinic.model.Patient;

import java.util.List;

public interface DaoPatients {
    void create (Patient patient) throws CreatePatientException;
    void delete (Patient patient) throws DeletePatientException;
    void update (Patient patient);
    List<Patient> getAll();
}
