package com.haulmont.clinic.dao.hibernate.utils;

import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.model.Recipe;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private SessionFactory sessionFactory;

    private static HibernateSessionFactory instance;

    public static HibernateSessionFactory getInstance(){
        if(instance == null){
            instance = new HibernateSessionFactory();
        }
        return instance;
    }

    private HibernateSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Doctor.class);
        configuration.addAnnotatedClass(Patient.class);
        configuration.addAnnotatedClass(Recipe.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
