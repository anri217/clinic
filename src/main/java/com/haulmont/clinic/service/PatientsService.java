package com.haulmont.clinic.service;

import com.haulmont.clinic.model.Patient;

import java.util.List;

public interface PatientsService {
    List<Patient> getAll();
    void create (Patient patient);
    void update (Patient patient);
    void delete (Patient patient);
}
