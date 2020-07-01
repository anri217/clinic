package com.haulmont.clinic.view.doctorsUI;

import com.haulmont.clinic.dao.exceptions.daoDoctors.DeleteDoctorException;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.haulmont.clinic.view.doctorsUI.AddDoctorWindow;
import com.haulmont.clinic.view.doctorsUI.EditDoctorWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Theme(ValoTheme.THEME_NAME)
public class DoctorsUI extends VerticalLayout implements View {
    private Button addButton;
    private Button delButton;
    private Button editButton;
    private Button recNavBut;
    private Button patNavBut;

    private DoctorsService doctorsService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent request) {
        setMargin(true);

        Label docLabel = new Label("Doctors");

        addComponent(docLabel);

        Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);

        doctorsService = DoctorsServiceImpl.getInstance();

        List<Doctor> doctors = doctorsService.getAll();

        doctorGrid.setItems(doctors);

        doctorGrid.removeColumn("id");
        doctorGrid.setColumns("firstName", "lastName", "patronymic", "specialization");
        MultiSelectionModel<Doctor> selectionModel
                = (MultiSelectionModel<Doctor>) doctorGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        doctorGrid.setSizeFull();

        addComponent(doctorGrid);

        addButton = new Button(UIConstants.ADD_BUTTON);
        delButton = new Button(UIConstants.DELETE_BUTTON);
        recNavBut = new Button(UIConstants.NAV_TO_RECIPES);
        patNavBut = new Button(UIConstants.NAV_TO_PATIENTS);
        editButton = new Button("Edit");

        delButton.setEnabled(false);
        editButton.setEnabled(false);

        selectionModel.addMultiSelectionListener(event -> {
            delButton.setEnabled(event.getNewSelection().size() > 0);
            editButton.setEnabled(event.getNewSelection().size() == 1);
        });

        delButton.addClickListener(clickEvent -> {
            Set<Doctor> doctorSet = selectionModel.getSelectedItems();
            try {
                for (Doctor doc : doctorSet) {
                    doctorsService.delete(doc);
                }
            } catch (DeleteDoctorException e) {
                e.printStackTrace();
            }
            UI.getCurrent().getPage().reload();
            delButton.setEnabled(false);
        });

        addButton.addClickListener(clickEvent -> {
            AddDoctorWindow addWindow = new AddDoctorWindow();
            UI.getCurrent().addWindow(addWindow);
        });

        editButton.addClickListener(clickEvent -> {
            Set<Doctor> doctorSet = selectionModel.getSelectedItems();
            Iterator<Doctor> iter = doctorSet.iterator();
            EditDoctorWindow editDoctorWindow = new EditDoctorWindow(iter.next());
            UI.getCurrent().addWindow(editDoctorWindow);
        });

        recNavBut.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(UIConstants.RECIPES_VIEW);
            removeAllComponents();
        });

        patNavBut.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(UIConstants.PATIENTS_VIEW);
            removeAllComponents();
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.addComponent(addButton);
        horizontalLayout.addComponent(delButton);
        horizontalLayout.addComponent(editButton);
        horizontalLayout.addComponent(recNavBut);
        horizontalLayout.addComponent(patNavBut);

        addComponent(horizontalLayout);
    }
}