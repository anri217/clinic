package com.haulmont.clinic.view.doctorsUI;

import com.haulmont.clinic.factories.DoctorFactory;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.vaadin.ui.*;

public class EditDoctorWindow extends Window {
    public EditDoctorWindow(Doctor doctor){
        super("EDIT");

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        // Put some components in it
        TextField fNameTextField = new TextField(UIConstants.FIRST_NAME);
        fNameTextField.setMaxLength(20);
        fNameTextField.setValue(doctor.getFirstName());

        TextField lNameTextField = new TextField(UIConstants.LAST_NAME);
        lNameTextField.setMaxLength(40);
        lNameTextField.setValue(doctor.getLastName());

        TextField patTextField = new TextField(UIConstants.PATRONYMIC);
        patTextField.setMaxLength(40);
        patTextField.setValue(doctor.getPatronymic());

        TextField specialization = new TextField(UIConstants.SPECIALIZATION);
        specialization.setMaxLength(20);
        specialization.setValue(doctor.getSpecialization());


        verticalLayout.addComponent(fNameTextField);
        verticalLayout.addComponent(lNameTextField);
        verticalLayout.addComponent(patTextField);
        verticalLayout.addComponent(specialization);

        Button subButton = new Button(UIConstants.SUBMIT);

        subButton.addClickListener(clickEvent -> {
            DoctorsService doctorsService = DoctorsServiceImpl.getInstance();
            Doctor newDoctor = DoctorFactory.createDoctor(doctor.getId(), fNameTextField.getValue(), lNameTextField.getValue(),
                    patTextField.getValue(), specialization.getValue());
            doctorsService.update(newDoctor);
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
