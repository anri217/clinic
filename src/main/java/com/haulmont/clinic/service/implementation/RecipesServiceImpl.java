package com.haulmont.clinic.service.implementation;

import com.haulmont.clinic.dao.exceptions.daoRecipes.CreateRecipeException;
import com.haulmont.clinic.dao.exceptions.daoRecipes.DeleteRecipeException;
import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.DaoPatients;
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
            return new RecipesServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Recipe> getAll() {
        return daoRecipes.getAll();
    }

    @Override
    public void create(Recipe recipe) throws CreateRecipeException {
        daoRecipes.create(recipe);
    }

    @Override
    public void delete(Recipe recipe) throws DeleteRecipeException {
        daoRecipes.delete(recipe);
    }

    @Override
    public void update(Recipe recipe) {
        daoRecipes.update(recipe);
    }
}
