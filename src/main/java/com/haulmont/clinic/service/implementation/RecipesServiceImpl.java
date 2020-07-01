package com.haulmont.clinic.service.implementation;

import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.DaoRecipes;
import com.haulmont.clinic.dao.hibernate.implementation.DaoManagerImpl;
import com.haulmont.clinic.model.Recipe;
import com.haulmont.clinic.service.RecipesService;

import java.util.List;

public class RecipesServiceImpl implements RecipesService {

    private static RecipesServiceImpl instance;
    private DaoRecipes daoRecipes;

    private RecipesServiceImpl() {
        DaoManager daoManager = DaoManagerImpl.getInstance();
        daoRecipes = daoManager.getDaoRecipes();
    }

    public static RecipesServiceImpl getInstance() {
        if (instance == null){
            instance = new RecipesServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Recipe> getAll() {
        return daoRecipes.getAll();
    }

    @Override
    public void create(Recipe recipe) {
        daoRecipes.create(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        daoRecipes.delete(recipe);
    }

    @Override
    public void update(Recipe recipe) {
        daoRecipes.update(recipe);
    }

    @Override
    public List<Recipe> getAllWhereDoctorId(Long id) {
        return daoRecipes.getAllWhereDoctorId(id);
    }

    @Override
    public List<Recipe> getAllWherePatientId(Long id) {
        return daoRecipes.getAllWherePatientId(id);
    }

    @Override
    public List<Recipe> getRecipesByDescOrPriority(String column, String pattern) {
        pattern = "%" + pattern + "%";
        return daoRecipes.getRecipesByDescOrPriority(column, pattern);
    }

    @Override
    public List<Recipe> getRecipesByPatient(String[] pattern) {
        int size = pattern.length;
        for (int i = 0; i < size; i++) {
            pattern[i] = "%" + pattern[i] + "%";
        }
        return daoRecipes.getRecipesByPatient(pattern);
    }
}
