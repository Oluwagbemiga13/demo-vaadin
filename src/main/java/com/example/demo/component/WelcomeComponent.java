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

    public WelcomeComponent() {
    }

    @PostConstruct
    public void init() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Label welcomeMessage = new Label("Welcome to our application!");

        // Create a vertical layout for the menu items
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setAlignItems(Alignment.CENTER);

        // Create buttons for each menu item and add them to the vertical layout
        Button logoutButton = new Button("Log out", e -> getUI().ifPresent(ui -> ui.navigate(LoginComponent.class)));
        /*e -> getUI().ifPresent(ui -> ui.navigate(CreateOrganDialog.class))*/
        Button createOrganButton = new Button("Create new Organ", e -> {
            CreateOrganDialog createOrganDialog = new CreateOrganDialog(organService);
            createOrganDialog.open();
        });
        Button createSymptomButton = new Button("Create new Symptom", e -> {/* Implement the logic for creating a new symptom */});

        Button manageOrgans = new Button("Manage Symptoms", e -> getUI().ifPresent((ui -> ui.navigate(ManageSymptomsComponent.class))));

        menuLayout.add(logoutButton, createOrganButton, createSymptomButton, manageOrgans);

        RouterLink backToLogin = new RouterLink("Back to Login", LoginComponent.class);

        add(menuLayout,backToLogin);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String username = event.getRouteParameters().get("username").orElse("Anonymous");
        usernameLabel.setText("Hello, " + username + "!");
    }

    private void displayOrganDialog(){

    }
}

