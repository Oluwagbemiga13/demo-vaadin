package com.example.demo.ui.tool;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@org.springframework.stereotype.Component
public class ButtonInitializer {

    public Button createNavButton(String text, Component currentUI, Class navigationTarget, String buttonWidth) {
        Button button = new Button(text, event -> currentUI.getUI().get().navigate(navigationTarget));
        button.setWidth(buttonWidth);
        return button;
    }

    public Button createActButton(String text, Runnable action, String buttonWidth){
        Button button = new Button(text);
        button.addClickListener(e -> action.run());
        button.setWidth(buttonWidth);
        return button;
    }

}
