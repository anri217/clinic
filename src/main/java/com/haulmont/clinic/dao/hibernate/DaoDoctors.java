package com.haulmont.clinic.dao.hibernate;

import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.dao.exceptions.daoDoctors.DeleteDoctorException;
import com.haulmont.clinic.model.Doctor;

public interface DaoDoctors {
    void create (Doctor doctor) throws CreateDoctorException;
    void delete (Doctor doctor) throws DeleteDoctorException;
}
