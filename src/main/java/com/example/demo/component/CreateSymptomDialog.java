package com.example.demo.component;

import com.example.demo.dto.SymptomDTO;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateSymptomDialog extends Dialog {

    private SymptomService symptomService;

    private Binder<SymptomDTO> binder = new Binder<>(SymptomDTO.class);

    public CreateSymptomDialog(SymptomService symptomService) {
        this.symptomService = symptomService;
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setSizeFull();

        // Set up the form layout with text fields for the symptom id and name
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        TextField nameField = new TextField("Symptom Name");
        nameField.setWidth("100%");
        formLayout.addFormItem(nameField, "Name");

        // Bind the text fields to the symptom DTO
        binder.forField(nameField)
                .asRequired("Symptom name is required")
                .bind(SymptomDTO::name, SymptomDTO::withName);

        // Add a button to save the new symptom
        Button saveButton = new Button("Save", e -> saveSymptom(nameField.getValue()));
        Button backButton = new Button("Back", e -> close());
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(saveButton);
        buttonsLayout.add(backButton);

        formLayout.addFormItem(buttonsLayout, "");

        layout.add(formLayout);
        add(layout);
    }

    private void saveSymptom(String name) {
        SymptomDTO symptomDTO = new SymptomDTO(null, name);
        try {
            // Validate the binder and populate the DTO with the form data
            binder.validate();
            binder.writeBean(symptomDTO);

            // Save the new symptom using the symptom service
            symptomService.saveSymptom(symptomDTO);

            Notification.show("Symptom created successfully.");
            close();
        } catch (ValidationException e) {
            Notification.show("Please fix the errors and try again.");
        }
    }
}
