package com.haulmont.clinic.view;

import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.dao.exceptions.daoManager.ExecuteSqlStartScriptException;
import com.haulmont.clinic.dao.hibernate.DaoManager;
import com.haulmont.clinic.dao.hibernate.implementation.DaoManagerImpl;
import com.haulmont.clinic.factories.DoctorFactory;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
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

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView(UIConstants.DOCTORS_VIEW, new DoctorsUI());
        navigator.addView(UIConstants.PATIENTS_VIEW, new PatientsUI());
        navigator.addView(UIConstants.RECIPES_VIEW, new RecipesUI());

        DoctorsService doctorsService = null;
        try {
            doctorsService = DoctorsServiceImpl.getInstance();
        } catch (ExecuteSqlStartScriptException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; ++i){
            try {
                doctorsService.create(DoctorFactory.createDoctor("" + i,
                        "" + i + 1, "" + i + 2, "" + i + 3));
            } catch (CreateDoctorException e) {
                e.printStackTrace();
            }
        }




    }
}
