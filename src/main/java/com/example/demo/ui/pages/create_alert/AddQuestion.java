package com.example.demo.ui.pages.create_alert;

import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.service.alert.AnswerService;
import com.example.demo.service.alert.QuestionService;
import com.example.demo.ui.dialogs.CreateQuestionDialog;
import com.example.demo.ui.tool.GridManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Route("create-alert/add-question")
public class AddQuestion extends FlexLayout {

    private ComboBox<String> severityComboBox;
    private Button selectUIButton;
    private AlertDTO passedAlertDto;
    private FormLayout alertInfoForm;
    private Map<String, TextField> alertInfoFieldsMap = new HashMap<>();

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @Autowired
    private GridManager gridManager;

    public AddQuestion() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        init();
    }

    public void init() {

        log.info("Session ID when retrieving AlertDTO: " + VaadinSession.getCurrent().getSession().getId());
        passedAlertDto = VaadinSession.getCurrent().getAttribute(AlertDTO.class);
        log.info("Passed allert : {}", passedAlertDto);
        HttpSession httpSession = ((VaadinServletRequest) VaadinRequest.getCurrent()).getHttpServletRequest().getSession();
        log.info("HttpSession ID: " + httpSession.getId());
        log.info("Session isNew: " + httpSession.isNew());
        // ... [Your logging and session handling code here]

        // AlertDTO information
        alertInfoForm = new FormLayout();

        // Adding fields to the form layout
        addAlertInfoField("Alert Message", passedAlertDto.getMessage());

        // Check for non-null DTOs and display their names
        if (passedAlertDto.getOrganSymptom() != null) {
            addAlertInfoField("Organ Symptom", passedAlertDto.getOrganSymptom().getFullName());
        }
        if (passedAlertDto.getPartOrgan() != null) {
            addAlertInfoField("Part Organ", passedAlertDto.getPartOrgan().getFullName());
        }
        if (passedAlertDto.getSymptomPart() != null) {
            addAlertInfoField("Symptom Part", passedAlertDto.getSymptomPart().getFullName());
        }

        // UI Type selection and Severity ComboBox
        severityComboBox = new ComboBox<>("Severity", "LOW", "MEDIUM", "HIGH");
        severityComboBox.setValue(passedAlertDto.getSeverity()); // Set the initial value from the DTO
        severityComboBox.addValueChangeListener(event -> {
            String newSeverity = event.getValue();
            passedAlertDto.setSeverity(newSeverity);
        });


        selectUIButton = new Button("Add question UI");
        selectUIButton.addClickListener(event -> {
            showDialogBasedOnUIType();

        });

        VerticalLayout mainLayout = new VerticalLayout(alertInfoForm, severityComboBox, selectUIButton);
        mainLayout.setSpacing(true);
        mainLayout.setPadding(true);
        mainLayout.setWidth("400px");  // Adjust based on your requirements

        add(mainLayout);
    }

    private void addAlertInfoField(String caption, String value) {
        TextField field = new TextField(caption);
        field.setValue(value != null ? value : "");  // Avoid null values. Set to empty string if null.
        field.setReadOnly(true);
        alertInfoForm.add(field);
        alertInfoFieldsMap.put(caption, field); // Store the field in the map
    }

    private void showDialogBasedOnUIType() {
        String selectedUI = severityComboBox.getValue();
        if (selectedUI == null) {
            Notification.show("Please select a UI type first.");
        } else {
            CreateQuestionDialog dialog = new CreateQuestionDialog(gridManager, questionService, answerService);
            dialog.open();
        }
    }
}

