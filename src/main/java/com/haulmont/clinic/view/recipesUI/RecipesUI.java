package com.haulmont.clinic.view.recipesUI;

import com.haulmont.clinic.model.Recipe;
import com.haulmont.clinic.service.RecipesService;
import com.haulmont.clinic.service.implementation.RecipesServiceImpl;
import com.haulmont.clinic.view.UIConstants;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Theme(ValoTheme.THEME_NAME)
public class RecipesUI extends VerticalLayout implements View {
    private Button addButton;
    private Button delButton;
    private Button editButton;
    private Button docNavBut;
    private Button patNavBut;

    private RecipesService recipesService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent request) {
        removeAllComponents();

        setMargin(true);

        Label recLabel = new Label(UIConstants.RECIPES_PAGE_NAME);

        addComponent(recLabel);

        Grid<Recipe> recipesGrid = new Grid<>(Recipe.class);

        recipesService = RecipesServiceImpl.getInstance();

        List<Recipe> recipes = recipesService.getAll();

        recipesGrid.setItems(recipes);

        recipesGrid.setColumns("description", "validity", "priority");
        recipesGrid.addColumn(Recipe::getDoctorFullName).setCaption("Doctor");
        recipesGrid.addColumn(Recipe::getPatientFullName).setCaption("Patient");
        recipesGrid.addColumn(Recipe::getCreationDateString).setCaption("Creation Date");

        MultiSelectionModel<Recipe> selectionModel
                = (MultiSelectionModel<Recipe>) recipesGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        recipesGrid.setSizeFull();

        addComponent(recipesGrid);

        addButton = new Button(UIConstants.ADD_BUTTON);
        delButton = new Button(UIConstants.DELETE_BUTTON);
        docNavBut = new Button(UIConstants.NAV_TO_DOCTORS);
        patNavBut = new Button(UIConstants.NAV_TO_PATIENTS);
        editButton = new Button(UIConstants.EDIT_BUTTON);

        delButton.setEnabled(false);
        editButton.setEnabled(false);

        selectionModel.addMultiSelectionListener(event -> {
            delButton.setEnabled(event.getNewSelection().size() > 0);
            editButton.setEnabled(event.getNewSelection().size() == 1);
        });

        delButton.addClickListener(clickEvent -> {
            Set<Recipe> recipeSet = selectionModel.getSelectedItems();
            for (Recipe recipe : recipeSet) {
                recipesService.delete(recipe);
            }
            UI.getCurrent().getPage().reload();
            delButton.setEnabled(false);
        });

        addButton.addClickListener(clickEvent -> {
            AddRecipeWindow addWindow = new AddRecipeWindow();
            UI.getCurrent().addWindow(addWindow);
        });

        editButton.addClickListener(clickEvent -> {
            Set<Recipe> recipeSet = selectionModel.getSelectedItems();
            Iterator<Recipe> iter = recipeSet.iterator();
            EditRecipeWindow editRecipeWindow = new EditRecipeWindow(iter.next());
            UI.getCurrent().addWindow(editRecipeWindow);
        });

        docNavBut.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(UIConstants.DOCTORS_VIEW);
            removeAllComponents();
        });

        patNavBut.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(UIConstants.PATIENTS_VIEW);
            removeAllComponents();
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.addComponent(addButton);
        horizontalLayout.addComponent(delButton);
        horizontalLayout.addComponent(editButton);
        horizontalLayout.addComponent(docNavBut);
        horizontalLayout.addComponent(patNavBut);

        addComponent(horizontalLayout);

        HorizontalLayout filterFields = new HorizontalLayout();

        ComboBox<String> columnComboBox = new ComboBox<>("Filter: ");
        List<String> columns = new ArrayList<>();
        columns.add("Patient");
        columns.add("Description");
        columns.add("Priority");
        columnComboBox.setItems(columns);

        TextField patternPatient = new TextField(UIConstants.DESC_FOR_TEXT_FIELD_ENTER_PATIENT);
        patternPatient.setVisible(false);

        TextField patternDesc = new TextField("By: ");
        patternDesc.setVisible(false);

        ComboBox<String> priorityComboBox =
                new ComboBox<>("By: ");
        List<String> priorityList = new ArrayList<>();
        priorityList.add("Normal");
        priorityList.add("Cito");
        priorityList.add("Statim");
        priorityComboBox.setItems(priorityList);
        priorityComboBox.setVisible(false);

        columnComboBox.addSelectionListener(singleSelectionEvent -> {
            if (columnComboBox.getValue() != null) {
                priorityComboBox.setVisible(columnComboBox.getValue().equals("Priority"));
                patternDesc.setVisible(!columnComboBox.getValue().equals("Priority") &&
                        !columnComboBox.getValue().equals("Patient"));
                patternPatient.setVisible(!columnComboBox.getValue().equals("Priority") &&
                        !columnComboBox.getValue().equals("Description"));
            }
            else{
                priorityComboBox.setVisible(false);
                patternDesc.setVisible(false);
                patternPatient.setVisible(false);
            }
        });

        Button subFilter = new Button(UIConstants.SUBMIT_BUTTON);

        subFilter.addClickListener(clickEvent -> {
           String column = columnComboBox.getValue();
           List<Recipe> filteredRecipes;
           if (column != null && column.equals("Patient") && !patternPatient.getValue().equals("")){
               String[] patterns = patternPatient.getValue().split(" ");
               if (patterns.length <= 3) {
                   filteredRecipes = recipesService.getRecipesByPatient(patterns);
                   recipesGrid.setItems(filteredRecipes);
               }
               else{
                   new Notification(UIConstants.NOTIFICATION_TITLE,
                           UIConstants.FILTER_ENTER_PATIENT_ERROR,
                           Notification.Type.WARNING_MESSAGE, true).show(UI.getCurrent().getPage());
               }
           }
           else if(column != null && !patternDesc.getValue().equals("")){
               filteredRecipes = recipesService.getRecipesByDescOrPriority(columnComboBox.getValue(), patternDesc.getValue());
               recipesGrid.setItems(filteredRecipes);
           }
           else if(column != null && priorityComboBox.getValue() != null){
               filteredRecipes = recipesService.getRecipesByDescOrPriority(columnComboBox.getValue(),
                       priorityComboBox.getValue());
               recipesGrid.setItems(filteredRecipes);
           }
           else{
               new Notification(UIConstants.NOTIFICATION_TITLE,
                       UIConstants.FILTER_ENTER_VALUE_ERROR,
                       Notification.Type.WARNING_MESSAGE, true).show(UI.getCurrent().getPage());
           }
        });

        filterFields.addComponent(columnComboBox);
        filterFields.addComponent(patternDesc);
        filterFields.addComponent(patternPatient);
        filterFields.addComponent(priorityComboBox);

        addComponent(filterFields);
        addComponent(subFilter);
    }
}
