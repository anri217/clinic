package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.DaoErrorConstants;
import com.haulmont.clinic.dao.hibernate.exceptions.ExecuteSqlScriptException;
import com.haulmont.clinic.dao.hibernate.DaoDoctors;
import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.DaoPatients;
import com.haulmont.clinic.dao.hibernate.DaoRecipes;
import com.haulmont.clinic.dao.hibernate.utils.HibernateSessionFactory;
import org.hibernate.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DaoManagerImpl implements DaoManager{
    private static DaoManagerImpl instance;
    private SessionFactory sessionFactory;

    private DaoManagerImpl() {
        sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    private DaoManagerImpl(String path) throws ExecuteSqlScriptException {
        executeSqlStartScript(path);
        sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    public static DaoManagerImpl getInstance() {
        if (instance == null)
            instance = new DaoManagerImpl();
        return instance;
    }

    public static DaoManagerImpl getInstance(String path) throws ExecuteSqlScriptException {
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

    private void executeSqlStartScript(String path) throws ExecuteSqlScriptException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            StringBuilder query = new StringBuilder();
            while ((line = br.readLine()) != null) {
                query.append(line);
            }
            line = query.toString();
            String[] newLine = line.split(";");
            Session session = HibernateSessionFactory.getInstance().getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            for (String s : newLine) {
                SQLQuery sqlQuery = session.createSQLQuery(s);
                sqlQuery.executeUpdate();
            }
            tx.commit();
            session.close();
        } catch (IOException e) {
            throw new ExecuteSqlScriptException(DaoErrorConstants.EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }
}
