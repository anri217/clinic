package com.haulmont.clinic.dao.hibernate;

import com.haulmont.clinic.dao.exceptions.daoRecipes.CreateRecipeException;
import com.haulmont.clinic.dao.exceptions.daoRecipes.DeleteRecipeException;
import com.haulmont.clinic.model.Recipe;

public interface DaoRecipes {
    void create (Recipe recipe) throws CreateRecipeException;
    void delete (Recipe recipe) throws DeleteRecipeException;
}
