package com.haulmont.clinic.factories;

import com.haulmont.clinic.model.Patient;

public class PatientFactory {
    public static Patient createPatient(Long id, String firstName, String lastName, String patronymic,
                                        String phoneNumber){
        return new Patient(id, firstName, lastName, patronymic, phoneNumber);
    }

    public static Patient createPatient(String firstName, String lastName, String patronymic,
                                        String phoneNumber){
        return new Patient(firstName, lastName, patronymic, phoneNumber);
    }
}
