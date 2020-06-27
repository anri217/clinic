package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.DaoErrorConstants;
import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.dao.exceptions.daoPatients.DeletePatientException;
import com.haulmont.clinic.dao.exceptions.daoRecipes.CreateRecipeException;
import com.haulmont.clinic.dao.exceptions.daoRecipes.DeleteRecipeException;
import com.haulmont.clinic.dao.hibernate.DaoRecipes;
import com.haulmont.clinic.model.Recipe;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DaoRecipesImpl implements DaoRecipes {
    private final SessionFactory sessionFactory;

    public DaoRecipesImpl(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public void create(Recipe recipe) throws CreateRecipeException {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(recipe);
            tx.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new CreateRecipeException(DaoErrorConstants.CREATE_RECIPE_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void delete(Recipe recipe) throws DeleteRecipeException {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(recipe);
            tx.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new DeleteRecipeException(DaoErrorConstants.DELETE_RECIPE_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
