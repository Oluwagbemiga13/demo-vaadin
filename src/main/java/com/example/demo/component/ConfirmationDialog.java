package com.example.demo.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class ConfirmationDialog extends Dialog {

    private final Div dialogContent = new Div();
    private final Button cancelButton;
    private final Button confirmButton;

    public ConfirmationDialog(String message, Runnable onConfirm) {
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setSizeFull();

        // Set up the form layout with a label for the message
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        Label messageLabel = new Label(message);
        formLayout.addFormItem(messageLabel, "Message");

        //confirmButton.setEnabled(true);

        // Add buttons to the layout
        confirmButton = new Button("Confirm", e -> {
            onConfirm.run();
            close();
        });
        cancelButton = new Button("Cancel", e -> close());
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(confirmButton, cancelButton);
        buttonsLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        formLayout.addFormItem(buttonsLayout, "");

        layout.add(formLayout);
        add(layout);
    }
}
