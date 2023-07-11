package com.example.demo.ui.dialogs;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.service.simple.OrganService;
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
public class CreateOrganDialog extends Dialog {

    private OrganService organService;

    private Binder<OrganDTO> binder = new Binder<>(OrganDTO.class);

    public CreateOrganDialog(OrganService organService) {
        this.organService = organService;
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setSizeFull();

        // Set up the form layout with a text field for the organ name
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        TextField nameField = new TextField("Organ Name");
        nameField.setWidth("100%");
        formLayout.addFormItem(nameField, "Name");

        // Bind the text field to the organ DTO
        binder.forField(nameField)
                .bind(OrganDTO::getName, OrganDTO::setName);

        // Add a button to save the new organ
        Button saveButton = new Button("Save", e -> {
            saveOrgan(nameField.getValue());
            close();
        });
        Button backButton = new Button("Back", e -> close());
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(saveButton);
        buttonsLayout.add(backButton);

        formLayout.addFormItem(buttonsLayout, "");

        layout.add(formLayout);
        add(layout);
    }

    private void saveOrgan(String name) {
        OrganDTO organDTO = new OrganDTO(null, name);
        try {
            // Validate the binder and populate the DTO with the form data
            binder.validate();
            binder.writeBean(organDTO);

            // Save the new organ using the organ service
            organService.save(organDTO);

            Notification.show("Organ created successfully.");
            close();
        } catch (ValidationException e) {
            Notification.show("Please fix the errors and try again.");
        }
    }
}
