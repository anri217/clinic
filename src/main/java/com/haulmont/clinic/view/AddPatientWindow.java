package com.haulmont.clinic.view;

import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.dao.exceptions.daoPatients.CreatePatientException;
import com.haulmont.clinic.factories.DoctorFactory;
import com.haulmont.clinic.factories.PatientFactory;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.PatientsService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
import com.haulmont.clinic.service.implementation.PatientsServiceImpl;
import com.vaadin.ui.*;

public class AddPatientWindow extends Window {
    public AddPatientWindow(){
        super(UIConstants.ADD_WINDOW);

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        // Put some components in it
        TextField fNameTextField = new TextField(UIConstants.FIRST_NAME);
        fNameTextField.setMaxLength(20);

        TextField lNameTextField = new TextField(UIConstants.LAST_NAME);
        lNameTextField.setMaxLength(40);

        TextField patTextField = new TextField(UIConstants.PATRONYMIC);
        patTextField.setMaxLength(40);

        TextField phoneTextField = new TextField(UIConstants.PHONE_NUMBER);
        phoneTextField.setMaxLength(15);

        verticalLayout.addComponent(fNameTextField);
        verticalLayout.addComponent(lNameTextField);
        verticalLayout.addComponent(patTextField);
        verticalLayout.addComponent(phoneTextField);

        Button subButton = new Button(UIConstants.SUBMIT);

        subButton.addClickListener(clickEvent -> {
            PatientsService patientsService = PatientsServiceImpl.getInstance();
            Patient patient = PatientFactory.createPatient(fNameTextField.getValue(), lNameTextField.getValue(),
                    patTextField.getValue(), phoneTextField.getValue());
            try {
                patientsService.create(patient);
            } catch (CreatePatientException e) {
                e.printStackTrace();
            }
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
