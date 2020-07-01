package com.haulmont.clinic.dao.hibernate;

import com.haulmont.clinic.model.Doctor;

import java.util.List;

public interface DaoDoctors {
    void create (Doctor doctor);
    void delete (Doctor doctor);
    void update (Doctor doctor);
    List<Doctor> getAll();
}
