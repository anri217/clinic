package com.haulmont.clinic.dao.hibernate;

public interface DaoManager {
    DaoDoctors getDaoDoctors();

    DaoPatients getDaoPatients();

    DaoRecipes getDaoRecipes();
}
