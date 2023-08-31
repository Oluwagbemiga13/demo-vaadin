package com.example.demo.ui.pages.create_alert;

import com.example.demo.dto.join.JoinItemDTO;
import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.service.alert.AlertService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Route("create-alert/add-message")
@Slf4j
public class AddMessageView extends VerticalLayout {

    @Autowired
    private AlertService alertService;

    private H2 itemNameLabel;
    private TextArea messageField;
    private Button addMessageButton;
    private Button backButton;

    private JoinItemDTO passedItem;



    public AddMessageView() {
        passedItem = VaadinSession.getCurrent().getAttribute(JoinItemDTO.class);

        // Display the name
        if (passedItem != null) {
            itemNameLabel = new H2("Selected Name: " + passedItem.getFullName());
        } else {
            itemNameLabel = new H2("No item selected");
        }

        // Instruction label
        Label instructionLabel = new Label("Write a message you want to attach to the alert:");

        // Multi-line text field for user message
        messageField = new TextArea();
        messageField.setPlaceholder("Enter your message here...");
        messageField.setHeight("200px"); // adjust as required
        messageField.setWidth("800px");

        // Button to retrieve the message
        addMessageButton = new Button("Add Message", clickEvent -> {
            String userMessage = messageField.getValue();
            // Process the user message as required
            if (userMessage.isEmpty()) {
                Notification.show("Message field required!");
            }
            else {
                AlertDTO alert = new AlertDTO();
                try {
                    alert = alertService.saveAlertFromJoinDTO(passedItem);
                    alert.setMessage(userMessage);
                    log.info(alert.toString());
                } catch (DataIntegrityViolationException e) {
                    log.error(e.getMessage());

                    // Create the dialog
                    Dialog dialog = new Dialog();
                    dialog.setModal(true);

                    // Message for the dialog
                    Label messageLabel = new Label("Alert with this combination of Entities already exists. Do you want to change it?");
                    dialog.add(messageLabel);

                    // Create buttons and their listeners
                    Button yesButton = new Button("Yes", event -> {
                        // Fetch from DB or perform whatever action you need
                        // For example: alert = alertService.fetchAlertFromDB(passedItem);
                        log.info("Clicked yes.");

                        dialog.close();
                    });
                    Button noButton = new Button("No", event -> dialog.close());

                    // Add buttons to the dialog
                    HorizontalLayout buttonLayout = new HorizontalLayout(yesButton, noButton);
                    buttonLayout.setAlignItems(Alignment.CENTER);
                    dialog.add(buttonLayout);

                    // Show the dialog
                    dialog.open();
                }
                if (alert.getId() != null) {
                    Notification.show("Success");
                    VaadinSession.getCurrent().setAttribute(AlertDTO.class, alert);
                    log.info("Session ID when setting AlertDTO: " + VaadinSession.getCurrent().getSession().getId());
                    HttpSession httpSession = ((VaadinServletRequest) VaadinRequest.getCurrent()).getHttpServletRequest().getSession();
                    log.info("HttpSession ID: " + httpSession.getId());
                    log.info("Session isNew: " + httpSession.isNew());
                    UI.getCurrent().navigate(AddQuestion.class);
                }
            }
        });

        // Button to navigate back to the previous view
        backButton = new Button("Go Back", clickEvent -> {
            UI.getCurrent().navigate("create-alert"); // Assuming the previous view route is ""
        });

        // Grouping the buttons using HorizontalLayout
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.add(backButton, addMessageButton);

        // Setting up the main layout with padding, spacing and alignment
        setAlignItems(FlexComponent.Alignment.CENTER);  // centers the content horizontally
        setJustifyContentMode(JustifyContentMode.CENTER);  // centers the content vertically
        setPadding(true);
        setSpacing(true);

        // Adding components to the layout
        add(itemNameLabel, instructionLabel, messageField, buttonLayout);
    }
}

