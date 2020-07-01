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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EditRecipeWindow extends Window {
    public EditRecipeWindow(Recipe recipe){
        super("EDIT");

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        // Put some components in it
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

        Button subButton = new Button(UIConstants.SUBMIT);

        subButton.addClickListener(clickEvent -> {
            RecipesService recipesService = RecipesServiceImpl.getInstance();
            Recipe newRecipe = RecipeFactory.createRecipe(recipe.getId(), descTextArea.getValue(), patientComboBox.getValue(),
                    doctorComboBox.getValue(), recipe.getCreationDate(), Integer.parseInt(validityTextField.getValue()),
                    priorityComboBox.getValue());
            recipesService.update(newRecipe);
            this.close();
            UI.getCurrent().getPage().reload();
        });

        verticalLayout.addComponent(subButton);

        // Center it in the browser window
        center();
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setHeight("600px");
        setWidth("350px");
    }
}
