package com.haulmont.clinic.view;

import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.factories.PatientFactory;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.service.PatientsService;
import com.haulmont.clinic.service.implementation.PatientsServiceImpl;
import com.vaadin.ui.*;

public class EditPatientWindow extends Window {
    public EditPatientWindow(Patient patient){
        super("EDIT");

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        // Put some components in it
        TextField fNameTextField = new TextField(UIConstants.FIRST_NAME);
        fNameTextField.setMaxLength(20);
        fNameTextField.setValue(patient.getFirstName());

        TextField lNameTextField = new TextField(UIConstants.LAST_NAME);
        lNameTextField.setMaxLength(40);
        lNameTextField.setValue(patient.getLastName());

        TextField patTextField = new TextField(UIConstants.PATRONYMIC);
        patTextField.setMaxLength(40);
        patTextField.setValue(patient.getPatronymic());

        TextField phoneTextField = new TextField(UIConstants.PHONE_NUMBER);
        phoneTextField.setMaxLength(15);
        phoneTextField.setValue(patient.getPhoneNumber());

        verticalLayout.addComponent(fNameTextField);
        verticalLayout.addComponent(lNameTextField);
        verticalLayout.addComponent(patTextField);
        verticalLayout.addComponent(phoneTextField);

        Button subButton = new Button(UIConstants.SUBMIT);

        subButton.addClickListener(clickEvent -> {
            PatientsService patientsService = PatientsServiceImpl.getInstance();
            Patient newPatient = PatientFactory.createPatient(patient.getId(), fNameTextField.getValue(), lNameTextField.getValue(),
                    patTextField.getValue(), phoneTextField.getValue());
            patientsService.update(newPatient);
            this.close();
            UI.getCurrent().getPage().reload();
        });

        verticalLayout.addComponent(subButton);

        // Center it in the browser window
        center();
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setHeight("400px");
        setWidth("250px");
    }
}
