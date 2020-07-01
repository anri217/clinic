package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.DaoConstants;
import com.haulmont.clinic.dao.hibernate.DaoRecipes;
import com.haulmont.clinic.model.Recipe;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DaoRecipesImpl implements DaoRecipes {
    private final SessionFactory sessionFactory;

    public DaoRecipesImpl(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public void create(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(recipe);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(recipe);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(recipe);
        tx.commit();
        session.close();
    }

    @Override
    public List<Recipe> getAll() {
        List<Recipe> recipes = new ArrayList<>();
        Session session = sessionFactory.openSession();
        String hql = "From Recipe";
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        for (Object o : query.list()) {
            recipes.add((Recipe) o);
        }
        tx1.commit();
        session.close();
        return recipes;
    }

    @Override
    public List<Recipe> getAllWhereDoctorId(Long id) {
        List<Recipe> recipes = new ArrayList<>();
        Session session = sessionFactory.openSession();
        String hql = "From Recipe where doctor.id = :id";
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter(DaoConstants.ID, id);
        for (Object o : query.list()) {
            recipes.add((Recipe) o);
        }
        tx1.commit();
        session.close();
        return recipes;
    }

    @Override
    public List<Recipe> getAllWherePatientId(Long id) {
        List<Recipe> recipes = new ArrayList<>();
        Session session = sessionFactory.openSession();
        String hql = "From Recipe where patient.id = :id";
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter(DaoConstants.ID, id);
        for (Object o : query.list()) {
            recipes.add((Recipe) o);
        }
        tx1.commit();
        session.close();
        return recipes;
    }

    @Override
    public List<Recipe> getRecipesByDescOrPriority(String column, String pattern) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Recipe WHERE %s LIKE :pattern";
        column = column.toLowerCase();
        hql = String.format(hql, column);
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        List<Recipe> recipes = new ArrayList<>();
        query.setParameter(DaoConstants.PATTERN, pattern);
        for (Object o : query.list()) {
            recipes.add((Recipe) o);
        }
        tx1.commit();
        session.close();
        return recipes;
    }

    @Override
    public List<Recipe> getRecipesByPatient(String[] pattern) {
        Session session = sessionFactory.openSession();
        int size = pattern.length;
        String hql;
        List<Recipe> recipes = new ArrayList<>();
        if (size == 1) {
            hql = "FROM Recipe WHERE patient.firstName LIKE :pattern OR patient.lastName LIKE :pattern OR " +
                    "patient.patronymic LIKE :pattern";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(DaoConstants.PATTERN, pattern[0]);
            for (Object o : query.list()) {
                recipes.add((Recipe) o);
            }
            tx1.commit();
            session.close();
        }
        else if (size == 2){
            hql = "FROM Recipe WHERE patient.firstName LIKE :pattern AND patient.lastName LIKE :pattern1";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(DaoConstants.PATTERN, pattern[0]);
            query.setParameter(DaoConstants.PATTERN_1, pattern[1]);
            for (Object o : query.list()) {
                recipes.add((Recipe) o);
            }
            tx1.commit();
            session.close();
        }
        else {
            hql = "FROM Recipe WHERE patient.firstName LIKE :pattern AND patient.lastName LIKE :pattern1 AND " +
                    "patient.patronymic LIKE :pattern2";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(DaoConstants.PATTERN, pattern[0]);
            query.setParameter(DaoConstants.PATTERN_1, pattern[1]);
            query.setParameter(DaoConstants.PATTERN_2, pattern[2]);
            for (Object o : query.list()) {
                recipes.add((Recipe) o);
            }
            tx1.commit();
            session.close();
        }
        return recipes;
    }
}
