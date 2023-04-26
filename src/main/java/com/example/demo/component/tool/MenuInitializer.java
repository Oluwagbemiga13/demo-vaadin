package com.example.demo.component.tool;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;



@Service
public class MenuInitializer {

    public HorizontalLayout createHorizontalMenu(Map<Button, Consumer<Button>> buttonActions) {
        HorizontalLayout menu = new HorizontalLayout();

        for (Map.Entry<Button, Consumer<Button>> entry : buttonActions.entrySet()) {
            Button button = entry.getKey();
            Consumer<Button> clickListener = entry.getValue();
            button.addClickListener(event -> clickListener.accept(button));
            menu.add(button);
        }

        return menu;
    }

    public HorizontalLayout createHorizontalMenu(List<Button> buttons) {
        HorizontalLayout menu = new HorizontalLayout();

        buttons.forEach(menu::add);

        return menu;
    }

    public VerticalLayout createVerticalMenu(Map<Button, Consumer<Button>> buttonActions) {
        VerticalLayout menu = new VerticalLayout();

        for (Map.Entry<Button, Consumer<Button>> entry : buttonActions.entrySet()) {
            Button button = entry.getKey();
            Consumer<Button> clickListener = entry.getValue();
            button.addClickListener(event -> clickListener.accept(button));
            menu.add(button);
        }

        return menu;
    }

    public VerticalLayout createVerticalMenu(List<Button> buttons) {
        VerticalLayout menu = new VerticalLayout();

        buttons.forEach(menu::add);

        return menu;
    }

    public Button createNavButton(String text, Component currentUI, Class navigationTarget, String buttonWidth) {
        Button button = new Button(text, event -> currentUI.getUI().get().navigate(navigationTarget));
        button.setWidth(buttonWidth);
        return button;
    }

}
