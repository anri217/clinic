package com.haulmont.clinic.service;

import com.haulmont.clinic.dao.exceptions.daoRecipes.CreateRecipeException;
import com.haulmont.clinic.dao.exceptions.daoRecipes.DeleteRecipeException;
import com.haulmont.clinic.model.Recipe;

import java.util.List;

public interface RecipesService {
    List<Recipe> getAll();
    void create(Recipe recipe) throws CreateRecipeException;
    void delete(Recipe recipe) throws DeleteRecipeException;
    void update(Recipe recipe);
}
