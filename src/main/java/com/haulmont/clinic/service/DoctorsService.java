package com.haulmont.clinic.service;

import com.haulmont.clinic.model.Doctor;

import java.util.List;

public interface DoctorsService {
    List<Doctor> getAll();
    void create (Doctor doctor);
    void delete (Doctor doctor);
    void update (Doctor doctor);
}
