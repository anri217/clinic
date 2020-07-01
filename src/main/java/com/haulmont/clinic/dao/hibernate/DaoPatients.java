package com.haulmont.clinic.dao.hibernate;

import com.haulmont.clinic.model.Patient;

import java.util.List;

public interface DaoPatients {
    void create (Patient patient);
    void delete (Patient patient);
    void update (Patient patient);
    List<Patient> getAll();
}
