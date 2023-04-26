package com.example.demo.component.tool;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import org.springframework.stereotype.Service;

@Service
public class ButtonInitializer {

    public Button createNavButton(String text, Component currentUI, Class navigationTarget, String buttonWidth) {
        Button button = new Button(text, event -> currentUI.getUI().get().navigate(navigationTarget));
        button.setWidth(buttonWidth);
        return button;
    }
}
