package com.haulmont.clinic.view;

import com.haulmont.clinic.dao.exceptions.daoDoctors.CreateDoctorException;
import com.haulmont.clinic.factories.DoctorFactory;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
import com.vaadin.ui.*;

public class AddDoctorWindow extends Window {
    public AddDoctorWindow(){
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

        TextField specTextField = new TextField(UIConstants.SPECIALIZATION);
        specTextField.setMaxLength(20);

        verticalLayout.addComponent(fNameTextField);
        verticalLayout.addComponent(lNameTextField);
        verticalLayout.addComponent(patTextField);
        verticalLayout.addComponent(specTextField);

        Button subButton = new Button(UIConstants.SUBMIT);

        subButton.addClickListener(clickEvent -> {
            DoctorsService doctorsService = DoctorsServiceImpl.getInstance();
            Doctor doctor = DoctorFactory.createDoctor(fNameTextField.getValue(), lNameTextField.getValue(),
                    patTextField.getValue(), specTextField.getValue());
            try {
                doctorsService.create(doctor);
            } catch (CreateDoctorException e) {
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
