package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.exceptions.daoManager.ExecuteSqlStartScriptException;
import com.haulmont.clinic.dao.DaoErrorConstants;
import com.haulmont.clinic.dao.hibernate.utils.HibernateSessionFactory;
import com.haulmont.clinic.dao.hibernate.DaoDoctors;
import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.DaoPatients;
import com.haulmont.clinic.dao.hibernate.DaoRecipes;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DaoManagerImpl implements DaoManager{
    private static DaoManagerImpl instance;
    private SessionFactory sessionFactory;

    private DaoManagerImpl(String path) throws ExecuteSqlStartScriptException {
        executeSqlStartScript(path);
        sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    private DaoManagerImpl() {
        sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    public static DaoManagerImpl getInstance() {
        if (instance == null)
            instance = new DaoManagerImpl();
        return instance;
    }

    public static DaoManagerImpl getInstance(String path) throws ExecuteSqlStartScriptException {
        if (instance == null)
            instance = new DaoManagerImpl(path);
        return instance;
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    @Override
    public DaoDoctors getDaoDoctors() {
        return new DaoDoctorsImpl(getSessionFactory());
    }

    @Override
    public DaoPatients getDaoPatients() {
        return new DaoPatientsImpl(getSessionFactory());
    }

    @Override
    public DaoRecipes getDaoRecipes() {
        return new DaoRecipesImpl(getSessionFactory());
    }

    private void executeSqlStartScript(String path) throws ExecuteSqlStartScriptException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            Session session = HibernateSessionFactory.getInstance().getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(stringBuilder.toString());
            query.executeUpdate();
            tx.commit();
            session.close();
        } catch (IOException | HibernateException e) {
            throw new ExecuteSqlStartScriptException(DaoErrorConstants.EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }
}
