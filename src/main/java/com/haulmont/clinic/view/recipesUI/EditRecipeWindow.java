package com.haulmont.clinic.view.recipesUI;

import com.haulmont.clinic.factories.RecipeFactory;
import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.model.Patient;
import com.haulmont.clinic.model.Recipe;
import com.haulmont.clinic.service.DoctorsService;
import com.haulmont.clinic.service.PatientsService;
import com.haulmont.clinic.service.RecipesService;
import com.haulmont.clinic.service.implementation.DoctorsServiceImpl;
import com.haulmont.clinic.service.implementation.PatientsServiceImpl;
import com.haulmont.clinic.service.implementation.RecipesServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class EditRecipeWindow extends Window {
    public EditRecipeWindow(Recipe recipe){
        super(UIConstants.EDIT_WINDOW);

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        TextArea descTextArea = new TextArea("Description: ");
        descTextArea.setValue(recipe.getDescription());
        descTextArea.setWidth("300px");

        PatientsService patientsService = PatientsServiceImpl.getInstance();
        List<Patient> patients = patientsService.getAll();
        ComboBox<Patient> patientComboBox =
                new ComboBox<>("Select patient: ");
        patientComboBox.setItems(patients);
        patientComboBox.setItemCaptionGenerator(Patient::getFullName);
        patientComboBox.setWidth("300px");
        patientComboBox.setSelectedItem(recipe.getPatient());

        DoctorsService doctorsService = DoctorsServiceImpl.getInstance();
        List<Doctor> doctors = doctorsService.getAll();
        ComboBox<Doctor> doctorComboBox =
                new ComboBox<>("Select doctor: ");
        doctorComboBox.setItems(doctors);
        doctorComboBox.setItemCaptionGenerator(Doctor::getFullName);
        doctorComboBox.setWidth("300px");
        doctorComboBox.setSelectedItem(recipe.getDoctor());

        TextField validityTextField = new TextField("Validity(days): ");
        validityTextField.setMaxLength(2);
        validityTextField.setValue(recipe.getValidity().toString());

        ComboBox<String> priorityComboBox =
                new ComboBox<>("Select priority: ");
        List<String> priorityList = new ArrayList<>();
        priorityList.add("Normal");
        priorityList.add("Cito");
        priorityList.add("Statim");
        priorityComboBox.setItems(priorityList);
        priorityComboBox.setSelectedItem(recipe.getPriority());

        verticalLayout.addComponent(descTextArea);
        verticalLayout.addComponent(patientComboBox);
        verticalLayout.addComponent(doctorComboBox);
        verticalLayout.addComponent(validityTextField);
        verticalLayout.addComponent(priorityComboBox);

        Button okButton = new Button(UIConstants.OK);

        okButton.addClickListener(clickEvent -> {
            boolean isValidity = true;
            String validity = validityTextField.getValue();
            int size = validity.length();
            for (int i = 0; i < size && isValidity; i++) {
                isValidity = Character.isDigit(validity.charAt(i));
            }
            if (!descTextArea.getValue().equals("") && !validityTextField.getValue().equals("") &&
                    !priorityComboBox.getValue().equals("") && patientComboBox.getValue() != null &&
                    doctorComboBox.getValue() != null && isValidity) {
                RecipesService recipesService = RecipesServiceImpl.getInstance();
                Recipe newRecipe = RecipeFactory.createRecipe(recipe.getId(), descTextArea.getValue(), patientComboBox.getValue(),
                        doctorComboBox.getValue(), recipe.getCreationDate(), Integer.parseInt(validityTextField.getValue()),
                        priorityComboBox.getValue());
                recipesService.update(newRecipe);
                this.close();
                UI.getCurrent().getPage().reload();
            }
            else{
                new Notification(UIConstants.ERROR, UIConstants.ADD_OR_EDIT_RECIPE_ERROR,
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
        setHeight("600px");
        setWidth("350px");
    }
}
