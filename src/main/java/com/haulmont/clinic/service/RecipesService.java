package com.haulmont.clinic.service;

import com.haulmont.clinic.model.Recipe;

import java.util.List;

public interface RecipesService {
    List<Recipe> getAll();
    void create(Recipe recipe);
    void delete(Recipe recipe);
    void update(Recipe recipe);
    List<Recipe> getAllWhereDoctorId(Long id);
    List<Recipe> getAllWherePatientId(Long id);
    List<Recipe> getRecipesByDescOrPriority(String column, String pattern);
    List<Recipe> getRecipesByPatient(String pattern);
}
