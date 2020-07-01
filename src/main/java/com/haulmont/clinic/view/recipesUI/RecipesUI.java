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
        setMargin(true);

        Label recLabel = new Label("Recipes");

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
        editButton = new Button("Edit");

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

        TextField pattern = new TextField("By: ");
        pattern.setVisible(false);

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
                pattern.setVisible(!columnComboBox.getValue().equals("Priority"));
            }
            else{
                priorityComboBox.setVisible(false);
                pattern.setVisible(false);
            }
        });

        Button subFilter = new Button(UIConstants.SUBMIT);

        subFilter.addClickListener(clickEvent -> {
           String column = columnComboBox.getValue();
           List<Recipe> filteredRecipes;
           if (column != null && column.equals("Patient")){
               filteredRecipes = recipesService.getRecipesByPatient(pattern.getValue());
               recipesGrid.setItems(filteredRecipes);
           }
           else if(column != null){
               filteredRecipes = recipesService.getRecipesByDescOrPriority(columnComboBox.getValue(), pattern.getValue());
               recipesGrid.setItems(filteredRecipes);
           }
           else{
               new Notification("ERROR",
                       "Please, choose filter",
                       Notification.Type.WARNING_MESSAGE, true).show(UI.getCurrent().getPage());
           }
        });

        filterFields.addComponent(columnComboBox);
        filterFields.addComponent(pattern);
        filterFields.addComponent(priorityComboBox);

        addComponent(filterFields);
        addComponent(subFilter);
    }
}
