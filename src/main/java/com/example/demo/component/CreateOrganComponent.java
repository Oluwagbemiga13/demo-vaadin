package com.example.demo.component;

import com.example.demo.dto.OrganDTO;
import com.example.demo.service.OrganService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@Route("create-organ")
public class CreateOrganComponent extends Dialog {

    @Autowired
    private OrganService organService;
    private Binder<OrganDTO> binder = new Binder<>(OrganDTO.class);

    public CreateOrganComponent() {;
    }

    @PostConstruct
    public void init(){
        setSizeFull();

        // Set up the form layout with a text field for the organ name
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Organ Name");
        formLayout.add(nameField);

        // Bind the text field to the organ DTO
        binder.bind(nameField, OrganDTO::name, OrganDTO::withName);

        // Add a button to save the new organ
        Button saveButton = new Button("Save", e -> saveOrgan());
        formLayout.add(saveButton);

        add(formLayout);

    }

    private void saveOrgan() {
        OrganDTO organDTO = binder.getBean();
        try {
            // Validate the binder and populate the DTO with the form data
            binder.validate();
            binder.writeBean(organDTO);

            // Save the new organ using the organ service
            organService.saveOrgan(organDTO);

            Notification.show("Organ created successfully.");
            close();
        } catch (ValidationException e) {
            Notification.show("Please fix the errors and try again.");
        }
    }
}
