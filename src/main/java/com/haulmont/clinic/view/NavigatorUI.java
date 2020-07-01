package com.haulmont.clinic.view;

import com.haulmont.clinic.dao.DaoConstants;
import com.haulmont.clinic.dao.exceptions.daoManager.ExecuteSqlStartScriptException;
import com.haulmont.clinic.dao.hibernate.implementation.DaoManagerImpl;
import com.haulmont.clinic.view.doctorsUI.DoctorsUI;
import com.haulmont.clinic.view.patientsUI.PatientsUI;
import com.haulmont.clinic.view.recipesUI.RecipesUI;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class NavigatorUI extends UI {
    Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("CLINIC");

        try {
            DaoManagerImpl.getInstance(DaoConstants.PATH_TO_START_SCRIPT);
        } catch (ExecuteSqlStartScriptException e) {
            e.printStackTrace();
        }

        navigator = new Navigator(this, this);

        navigator.addView(UIConstants.DOCTORS_VIEW, new DoctorsUI());
        navigator.addView(UIConstants.PATIENTS_VIEW, new PatientsUI());
        navigator.addView(UIConstants.RECIPES_VIEW, new RecipesUI());
    }
}
