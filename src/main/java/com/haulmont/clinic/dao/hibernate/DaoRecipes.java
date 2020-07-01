package com.haulmont.clinic.dao.hibernate;

import com.haulmont.clinic.dao.exceptions.daoRecipes.CreateRecipeException;
import com.haulmont.clinic.dao.exceptions.daoRecipes.DeleteRecipeException;
import com.haulmont.clinic.model.Recipe;

import java.util.List;

public interface DaoRecipes {
    void create (Recipe recipe) throws CreateRecipeException;
    void delete (Recipe recipe) throws DeleteRecipeException;
    void update (Recipe recipe);
    List<Recipe> getAll();
}
