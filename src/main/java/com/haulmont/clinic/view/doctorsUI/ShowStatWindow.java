package com.haulmont.clinic.view.doctorsUI;

import com.haulmont.clinic.model.Doctor;
import com.haulmont.clinic.model.Recipe;
import com.haulmont.clinic.service.RecipesService;
import com.haulmont.clinic.service.implementation.RecipesServiceImpl;
import com.vaadin.ui.*;

import java.util.List;

public class ShowStatWindow extends Window {
    public ShowStatWindow(Doctor doctor){
        super("STAT FOR " + doctor.getFullName().toUpperCase());

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        RecipesService recipesService = RecipesServiceImpl.getInstance();

        List<Recipe> recipes = recipesService.getAllWhereDoctorId(doctor.getId());

        Grid<Recipe> recipesGrid = new Grid<>(Recipe.class);
        recipesGrid.setSizeFull();

        recipesGrid.setItems(recipes);

        recipesGrid.setColumns("description", "validity", "priority");
        recipesGrid.addColumn(Recipe::getDoctorFullName).setCaption("Doctor");
        recipesGrid.addColumn(Recipe::getPatientFullName).setCaption("Patient");
        recipesGrid.addColumn(Recipe::getCreationDateString).setCaption("Creation Date");

        Label count = new Label("Count: " + recipes.size());

        verticalLayout.addComponent(recipesGrid);
        verticalLayout.addComponent(count);

        Button okButton = new Button("OK");
        okButton.addClickListener(clickEvent -> {
            this.close();
        });

        verticalLayout.addComponent(okButton);

        center();
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setHeight("550px");
        setWidth("1300px");
    }
}
