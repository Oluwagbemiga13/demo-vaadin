package com.example.demo.component;

import com.example.demo.service.OrganService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@Route("welcome")
@RouteAlias("welcome/:username")
public class WelcomeComponent extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private OrganService organService;

    private Label usernameLabel = new Label();
    private static final String BUTTON_WIDTH = "200px";

    public WelcomeComponent() {
    }

    @PostConstruct
    public void init() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Display the welcome message with the username or "Daniel" as the default
        usernameLabel.setText("Hello, Daniel!");
        add(usernameLabel);

        Label welcomeMessage = new Label("Welcome to our application!");
        add(welcomeMessage);

        // Create a vertical layout for the menu items
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setAlignItems(Alignment.CENTER);

        // Create buttons for each menu item and add them to the vertical layout
        Button manageSymptoms = new Button("Manage Symptoms", e -> getUI().ifPresent((ui -> ui.navigate(ManageSymptoms.class))));
        manageSymptoms.setWidth(BUTTON_WIDTH);

        Button manageOrgans = new Button("Manage Organs", event -> getUI().ifPresent(ui -> ui.navigate(ManageOrgans.class)));
        manageOrgans.setWidth(BUTTON_WIDTH);

        Button manageBodyParts = new Button("Manage Body", event -> getUI().ifPresent(ui -> ui.navigate(ManageParts.class)));
        manageBodyParts.setWidth(BUTTON_WIDTH);

        Button manageLogs = new Button("Manage Logs");
        manageLogs.setWidth(BUTTON_WIDTH);

        Button questionnaires = new Button("Questionnaires");
        questionnaires.setWidth(BUTTON_WIDTH);

        Button notes = new Button("Notes");
        notes.setWidth(BUTTON_WIDTH);

        Button settings = new Button("Settings");
        settings.setWidth(BUTTON_WIDTH);

        Button helpAndSupport = new Button("Help & Support");
        helpAndSupport.setWidth(BUTTON_WIDTH);

        Button about = new Button("About");
        about.setWidth(BUTTON_WIDTH);

        menuLayout.add(manageSymptoms, manageOrgans, manageBodyParts, manageLogs, questionnaires, notes, settings, helpAndSupport, about);

        RouterLink backToLogin = new RouterLink("Back to Login", LoginComponent.class);

        add(menuLayout, backToLogin);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String username = event.getRouteParameters().get("username").orElse("Daniel");
        usernameLabel.setText("Hello, " + username + "!");
    }
}
