package com.haulmont.clinic.dao.hibernate.implementation;

import com.haulmont.clinic.dao.DaoConstants;
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

    private DaoManagerImpl() throws ExecuteSqlStartScriptException {
        executeSqlStartScript();
        sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    public static DaoManagerImpl getInstance() throws ExecuteSqlStartScriptException {
        if (instance == null)
            instance = new DaoManagerImpl();
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

    private void executeSqlStartScript() throws ExecuteSqlStartScriptException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DaoConstants.PATH_TO_START_SCRIPT));
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
        } catch (IOException | HibernateException e) {
            throw new ExecuteSqlStartScriptException(DaoErrorConstants.EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }
}
