package com.example.demo.ui.pages;

import com.example.demo.dto.simple.UserDTO;
import com.example.demo.service.simple.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
public class LoginComponent extends VerticalLayout {

    @Autowired
    private UserService userService;

    public LoginComponent() {
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login", e -> login(usernameField.getValue(), passwordField.getValue()));

        // Create a new button for creating a new user
        Button createUserButton = new Button("Create User Tolu");
        createUserButton.addClickListener(e -> createUserTolu());

        add(usernameField, passwordField, loginButton, createUserButton);
        setAlignItems(Alignment.CENTER);
    }

    private void login(String username, String password) {
        if (userService.authenticate(username, password)) {
            getUI().ifPresent(ui -> ui.navigate("welcome/" + username));
        } else {
            Notification.show("Invalid username or password.");
        }
    }

    private void createUserTolu() {
        UserDTO newUser = new UserDTO(null, "Tolu", "Ulot");
        userService.saveUser(newUser);
    }
}
