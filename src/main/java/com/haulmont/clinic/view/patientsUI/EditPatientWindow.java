package com.haulmont.clinic.view.patientsUI;

import com.haulmont.clinic.factories.PatientFactory;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.service.PatientsService;
import com.haulmont.clinic.service.implementation.PatientsServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.vaadin.ui.*;

public class EditPatientWindow extends Window {
    public EditPatientWindow(Patient patient){
        super("EDIT");

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

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

        Button okButton = new Button("OK");

        okButton.addClickListener(clickEvent -> {
            boolean validPhoneNum;
            String phoneNum = phoneTextField.getValue();
            int size = phoneNum.length();
            validPhoneNum = size >= 11;
            for (int i = 0; i < size && validPhoneNum; ++i){
                if (i != 0){
                    validPhoneNum = Character.isDigit(phoneNum.charAt(i));
                }
                else{
                    validPhoneNum = Character.isDigit(phoneNum.charAt(i)) || phoneNum.charAt(i) == '+';
                }
            }
            if (!fNameTextField.getValue().equals("") && !lNameTextField.getValue().equals("") && validPhoneNum) {
                PatientsService patientsService = PatientsServiceImpl.getInstance();
                Patient newPatient = PatientFactory.createPatient(patient.getId(), fNameTextField.getValue(), lNameTextField.getValue(),
                        patTextField.getValue(), phoneTextField.getValue());
                patientsService.update(newPatient);
                this.close();
                UI.getCurrent().getPage().reload();
            }
            else{
                new Notification("ERROR",
                        "Please, enter first name and last name or enter your correct phone number",
                        Notification.Type.WARNING_MESSAGE, true).show(UI.getCurrent().getPage());
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
