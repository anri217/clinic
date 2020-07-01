package com.haulmont.clinic.view.patientsUI;

import com.haulmont.clinic.factories.PatientFactory;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.service.PatientsService;
import com.haulmont.clinic.service.implementation.PatientsServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.vaadin.ui.*;

public class AddPatientWindow extends Window {
    public AddPatientWindow(){
        super(UIConstants.ADD_WINDOW);

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

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

        Button okButton = new Button(UIConstants.OK_BUTTON);

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
                Patient patient = PatientFactory.createPatient(fNameTextField.getValue(), lNameTextField.getValue(),
                        patTextField.getValue(), phoneTextField.getValue());
                patientsService.create(patient);
                this.close();
                UI.getCurrent().getPage().reload();
            }
            else{
                new Notification(UIConstants.NOTIFICATION_TITLE,
                        UIConstants.ADD_OR_EDIT_PATIENT_ERROR,
                        Notification.Type.WARNING_MESSAGE, true).show(UI.getCurrent().getPage());
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);

        Button cancelButton = new Button(UIConstants.CANCEL_BUTTON);

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
