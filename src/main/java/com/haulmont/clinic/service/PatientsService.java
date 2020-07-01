package com.haulmont.clinic.service;

import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.dao.exceptions.daoPatients.DeletePatientException;
import com.haulmont.clinic.model.Patient;

import java.util.List;

public interface PatientsService {
    List<Patient> getAll();
    void create (Patient patient) throws CreatePatientException;
    void update (Patient patient);
    void delete (Patient patient) throws DeletePatientException;
}
