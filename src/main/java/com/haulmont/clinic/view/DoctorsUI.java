package com.haulmont.clinic.view;

import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class DoctorsUI extends VerticalLayout implements View {
    private Button docNavBut;
    private Button recNavBut;
    private Button patNavBut;

    private DoctorsService doctorsService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent request) {
        setSizeFull();
        setMargin(true);

        Label docLabel = new Label("Doctors UI");

        addComponent(docLabel);

        Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);

        doctorGrid.setItems(doctorsService.getAll());

//        docNavBut = new Button(UIConstants.NAV_TO_DOCTORS);
//        recNavBut = new Button(UIConstants.NAV_TO_RECIPES);
//        patNavBut = new Button(UIConstants.NAV_TO_PATIENTS);
//
//        docNavBut.addClickListener(clickEvent -> {
//            UI.getCurrent().getPage().reload();
//            UI.getCurrent().getNavigator().navigateTo(UIConstants.DOCTORS_VIEW);
//        });
//        recNavBut.addClickListener(clickEvent -> {
//            UI.getCurrent().getPage().reload();
//            UI.getCurrent().getNavigator().navigateTo(UIConstants.RECIPES_VIEW);
//        });
//        patNavBut.addClickListener(clickEvent -> {
//            UI.getCurrent().getPage().reload();
//            UI.getCurrent().getNavigator().navigateTo(UIConstants.PATIENTS_VIEW);
//        });
//
//        HorizontalLayout horizontalLayout = new HorizontalLayout();
//
//        horizontalLayout.addComponent(docNavBut);
//        horizontalLayout.addComponent(recNavBut);
//        horizontalLayout.addComponent(patNavBut);
//
//        addComponent(horizontalLayout);
   }
}