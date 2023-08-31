package com.example.demo.ui.pages.create_alert;

import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.alert.AnswerDTO;
import com.example.demo.dto.alert.QuestionDTO;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.alert.AnswerService;
import com.example.demo.service.alert.QuestionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Route("question")
public class CreateEntityComponent extends VerticalLayout {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AlertService alertService;

    private TextField questionNameField;
    private TextField answerNameField;
    private TextField alertMessageField;
    private ComboBox<String> alertSeverityField;

    @PostConstruct
    void init() {
        // Question field
        questionNameField = new TextField("Question Name");
        add(questionNameField);

        // Answer field
        answerNameField = new TextField("Answer Name");
        add(answerNameField);

        // Alert fields
        alertMessageField = new TextField("Alert Message");
        add(alertMessageField);

        alertSeverityField = new ComboBox<>("Alert Severity");
        alertSeverityField.setItems("Low", "Medium", "High"); // some arbitrary options
        add(alertSeverityField);

        Button saveButton = new Button("Save", event -> saveEntities());
        add(saveButton);
    }

    private void saveEntities() {
        // Create DTOs
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName(questionNameField.getValue());

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setName(answerNameField.getValue());
        answerDTO.setQuestion(questionDTO); // set the question DTO

        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setMessage(alertMessageField.getValue());
        alertDTO.setSeverity(alertSeverityField.getValue());
        alertDTO.setQuestion(questionDTO); // set the question DTO

        // Save entities using the service layer
        questionService.save(questionDTO);
        answerService.save(answerDTO);
        alertService.save(alertDTO);

        // Clear the fields after saving
        questionNameField.clear();
        answerNameField.clear();
        alertMessageField.clear();
        alertSeverityField.clear();

        // Notify the user
        Notification.show("Entities saved successfully.");
    }
}

