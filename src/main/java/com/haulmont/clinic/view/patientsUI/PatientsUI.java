package com.haulmont.clinic.view.patientsUI;

import com.haulmont.clinic.dao.exceptions.daoPatients.DeletePatientException;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.service.PatientsService;
import com.haulmont.clinic.service.implementation.PatientsServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.haulmont.clinic.view.patientsUI.AddPatientWindow;
import com.haulmont.clinic.view.patientsUI.EditPatientWindow;
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
public class PatientsUI extends VerticalLayout implements View {
    private Button addButton;
    private Button delButton;
    private Button editButton;
    private Button recNavBut;
    private Button docNavBut;

    private PatientsService patientsService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent request) {
        setMargin(true);

        Label patLabel = new Label("Patients");

        addComponent(patLabel);

        Grid<Patient> patientGrid = new Grid<>(Patient.class);

        patientsService = PatientsServiceImpl.getInstance();

        List<Patient> patients = patientsService.getAll();

        patientGrid.setItems(patients);

        patientGrid.removeColumn("id");
        patientGrid.setColumns("firstName", "lastName", "patronymic", "phoneNumber");
        MultiSelectionModel<Patient> selectionModel
                = (MultiSelectionModel<Patient>) patientGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        patientGrid.setSizeFull();

        addComponent(patientGrid);

        addButton = new Button(UIConstants.ADD_BUTTON);
        delButton = new Button(UIConstants.DELETE_BUTTON);
        recNavBut = new Button(UIConstants.NAV_TO_RECIPES);
        docNavBut = new Button(UIConstants.NAV_TO_DOCTORS);
        editButton = new Button("Edit");

        delButton.setEnabled(false);
        editButton.setEnabled(false);

        selectionModel.addMultiSelectionListener(event -> {
            delButton.setEnabled(event.getNewSelection().size() > 0);
            editButton.setEnabled(event.getNewSelection().size() == 1);
        });

        delButton.addClickListener(clickEvent -> {
            Set<Patient> patientSet = selectionModel.getSelectedItems();
            try {
                for (Patient pat : patientSet) {
                    patientsService.delete(pat);
                }
            } catch (DeletePatientException e) {
                e.printStackTrace();
            }
            UI.getCurrent().getPage().reload();
            delButton.setEnabled(false);
        });

        addButton.addClickListener(clickEvent -> {
            AddPatientWindow addWindow = new AddPatientWindow();
            UI.getCurrent().addWindow(addWindow);
        });

        editButton.addClickListener(clickEvent -> {
            Set<Patient> patientSet = selectionModel.getSelectedItems();
            Iterator<Patient> iter = patientSet.iterator();
            EditPatientWindow editPatientWindow = new EditPatientWindow(iter.next());
            UI.getCurrent().addWindow(editPatientWindow);
        });

        recNavBut.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(UIConstants.RECIPES_VIEW);
            removeAllComponents();
        });

        docNavBut.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(UIConstants.DOCTORS_VIEW);
            removeAllComponents();
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.addComponent(addButton);
        horizontalLayout.addComponent(delButton);
        horizontalLayout.addComponent(editButton);
        horizontalLayout.addComponent(recNavBut);
        horizontalLayout.addComponent(docNavBut);

        addComponent(horizontalLayout);
    }
}
