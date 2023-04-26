package com.example.demo.component;

import com.example.demo.component.tool.ButtonInitializer;
import com.example.demo.component.tool.MenuInitializer;
import com.example.demo.component.tool.TestComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Route("welcome")
@RouteAlias("welcome/:username")
@RequiredArgsConstructor
public class WelcomeComponent extends VerticalLayout implements BeforeEnterObserver {

    private final MenuInitializer menuInitializer;

    private final ButtonInitializer buttonInitializer;

    private Label usernameLabel = new Label();
    private static final String MENU_BUTTON_WIDTH = "200px";


    @PostConstruct
    public void init() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        usernameLabel.setText("Hello, Daniel!");
        add(usernameLabel);

        Label welcomeMessage = new Label("Welcome to our application!");
        add(welcomeMessage);

        add(initMenu());


    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String username = event.getRouteParameters().get("username").orElse("Daniel");
        usernameLabel.setText("Hello, " + username + "!");
    }

    public VerticalLayout initMenu() {
        Button manageSymptoms = buttonInitializer.createNavButton("Manage Symptoms", this, ManageSymptoms.class, MENU_BUTTON_WIDTH);
        Button manageOrgans = buttonInitializer.createNavButton("Manage Organs", this, ManageOrgans.class, MENU_BUTTON_WIDTH);
        Button manageBodyParts = buttonInitializer.createNavButton("Manage Body", this, ManageParts.class, MENU_BUTTON_WIDTH);
        Button manageLogs = buttonInitializer.createNavButton("Manage Logs", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button questionnaires = buttonInitializer.createNavButton("Questionnaires", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button notes = buttonInitializer.createNavButton("Notes", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button settings = buttonInitializer.createNavButton("Settings", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button helpAndSupport = buttonInitializer.createNavButton("Help & Support", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button about = buttonInitializer.createNavButton("About", this, TestComponent.class, MENU_BUTTON_WIDTH);

        RouterLink backToLogin = new RouterLink("Back to Login", LoginComponent.class);

        List<Component> buttons = Arrays.asList(manageSymptoms, manageOrgans, manageBodyParts, manageLogs,
                questionnaires, notes, settings, helpAndSupport, about, backToLogin);

        return menuInitializer.createVerticalMenu(buttons);
    }
}
