package com.haulmont.clinic.factories;

import com.haulmont.clinic.model.Doctor;

public class DoctorFactory {
    public static Doctor createDoctor(Long id, String firstName, String lastName, String patronymic,
                                      String specialization){
        return new Doctor(id, firstName, lastName, patronymic, specialization);
    }

    public static Doctor createDoctor(String firstName, String lastName, String patronymic,
                                      String specialization){
        return new Doctor(firstName, lastName, patronymic, specialization);
    }
}
