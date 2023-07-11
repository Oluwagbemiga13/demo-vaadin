package com.example.demo.ui.dialogs;

import com.example.demo.dto.simple.DTO;
import com.example.demo.service.simple.EntityService;
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

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class EntityCreationDialog extends Dialog {


    public EntityCreationDialog(EntityService managingService) {
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setSizeFull();

        // Set up the form layout with text fields for the symptom id and name
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        String entityName = managingService.getEntityName();
        TextField nameField = new TextField(entityName + " Name");
        nameField.setWidth("100%");
        formLayout.addFormItem(nameField, "Name");

        Binder<DTO> binder = new Binder<>(managingService.getDTOClass());

        // Bind the text fields to the symptom DTO
        binder.forField(nameField)
                .asRequired(entityName + " name is required")
                .bind(DTO::getName, DTO::withName);

        // Add a button to save the new symptom
        Button saveButton = new Button("Save", e -> {
            try {
                saveEntity(nameField.getValue(), binder, managingService, entityName);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                     InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        });
        Button backButton = new Button("Back", e -> close());
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(saveButton);
        buttonsLayout.add(backButton);

        formLayout.addFormItem(buttonsLayout, "");

        layout.add(formLayout);
        add(layout);
    }

    private void saveEntity(String name, Binder<DTO> binder, EntityService managingService, String entityName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        DTO dto = (DTO) managingService.getDTOClass().getDeclaredConstructor(Long.class, String.class).newInstance(null, name);
        try {
            // Validate the binder and populate the DTO with the form data
            binder.validate();
            binder.writeBean(dto);

            // Save the new symptom using the symptom service
            managingService.save(dto);

            Notification.show(entityName + " created successfully.");
            close();
        } catch (ValidationException e) {
            Notification.show("Please fix the errors and try again.");
            log.info("Entity was not saved ", e);
        }
    }
}
