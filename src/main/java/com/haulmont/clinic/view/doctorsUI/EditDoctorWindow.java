package com.haulmont.clinic.view.doctorsUI;

import com.haulmont.clinic.factories.DoctorFactory;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.vaadin.ui.*;

public class EditDoctorWindow extends Window {
    public EditDoctorWindow(Doctor doctor){
        super(UIConstants.EDIT_WINDOW);

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

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

        Button okButton = new Button(UIConstants.OK);

        okButton.addClickListener(clickEvent -> {
            if (!fNameTextField.getValue().equals("") && !lNameTextField.getValue().equals("")) {
                DoctorsService doctorsService = DoctorsServiceImpl.getInstance();
                Doctor newDoctor = DoctorFactory.createDoctor(doctor.getId(), fNameTextField.getValue(), lNameTextField.getValue(),
                        patTextField.getValue(), specialization.getValue());
                doctorsService.update(newDoctor);
                this.close();
                UI.getCurrent().getPage().reload();
            }
            else{
                new Notification(UIConstants.ERROR,
                        UIConstants.ADD_OR_EDIT_DOCTOR_ERROR,
                        Notification.Type.WARNING_MESSAGE, true).show(UI.getCurrent().getPage());
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);

        Button cancelButton = new Button(UIConstants.CANCEL);

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
