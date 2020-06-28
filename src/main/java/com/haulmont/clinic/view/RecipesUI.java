package com.haulmont.clinic.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class RecipesUI extends VerticalLayout implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent request) {
        setSizeFull();
        setMargin(true);

        addComponent(new Label("Recipes UI"));
    }
}
