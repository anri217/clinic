package com.haulmont.clinic.view.doctorsUI;

import com.haulmont.clinic.factories.DoctorFactory;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.vaadin.ui.*;

public class AddDoctorWindow extends Window {
    public AddDoctorWindow(){
        super(UIConstants.ADD_WINDOW);

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

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

        Button okButton = new Button(UIConstants.OK);

        okButton.addClickListener(clickEvent -> {
            if (!fNameTextField.getValue().equals("") && !lNameTextField.getValue().equals("")) {
                DoctorsService doctorsService = DoctorsServiceImpl.getInstance();
                Doctor doctor = DoctorFactory.createDoctor(fNameTextField.getValue(), lNameTextField.getValue(),
                        patTextField.getValue(), specTextField.getValue());
                doctorsService.create(doctor);
                this.close();
                UI.getCurrent().getPage().reload();
            }
            else {
                new Notification(UIConstants.ERROR, UIConstants.ADD_DOCTOR_ERROR, Notification.Type.WARNING_MESSAGE,
                        true).show(UI.getCurrent().getPage());
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);

        Button cancelButton = new Button("Cancel");

        cancelButton.addClickListener(clickEvent -> {
            this.close();
        });

        horizontalLayout.addComponent(cancelButton);
        verticalLayout.addComponent(horizontalLayout);

        center();
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setHeight("400px");
        setWidth("250px");
    }
}
