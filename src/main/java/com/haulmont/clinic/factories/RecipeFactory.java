package com.haulmont.clinic.factories;

import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.model.Recipe;

import java.time.LocalDateTime;

public class RecipeFactory {
    public static Recipe createRecipe (Long id, String description, Patient patient, Doctor doctor, LocalDateTime creationDate,
                          Integer validity, String priority){
        return new Recipe(id, description, patient, doctor, creationDate, validity, priority);
    }

    public static Recipe createRecipe (String description, Patient patient, Doctor doctor, LocalDateTime creationDate,
                                       Integer validity, String priority){
        return new Recipe(description, patient, doctor, creationDate, validity, priority);
    }
}
