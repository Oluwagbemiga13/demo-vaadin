package com.example.demo.ui.pages.create_alert;

import com.example.demo.entity.alerts.Answer;
import com.example.demo.entity.alerts.Action; // Assuming this is where Action and ActionType are.
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestion extends Dialog {

    private TextField nameField;
    private TextField textField;
    private List<AnswerLayout> answerLayouts;

    public CreateQuestion() {
        initUI();
    }

    private void initUI() {
        FormLayout formLayout = new FormLayout();

        // Name
        nameField = new TextField("Name");
        formLayout.add(nameField);

        // Text
        textField = new TextField("Text");
        formLayout.add(textField);

        // Answers
        answerLayouts = new ArrayList<>();

        Button addButton = new Button("Add New Answer", e -> {
            AnswerLayout answerLayout = new AnswerLayout();
            answerLayouts.add(answerLayout);
            formLayout.add(answerLayout);
        });

        Button saveButton = new Button("Save", e -> saveQuestion());

        add(formLayout, addButton, saveButton);
    }

    private void saveQuestion() {
        // Implement save logic here
    }

    private class AnswerLayout extends FormLayout {
        private TextField answerNameField;
        private TextField answerTextField;
        private ComboBox<Action.ActionType> actionTypeComboBox;
        private Grid<?> questionsGrid; // Assuming Question class exists, replace '?' with 'Question'

        public AnswerLayout() {
            answerNameField = new TextField("Answer Name");
            add(answerNameField);

            answerTextField = new TextField("Answer");
            add(answerTextField);

            actionTypeComboBox = new ComboBox<>("Type of Action");
            actionTypeComboBox.setItems(Action.ActionType.values());
            actionTypeComboBox.addValueChangeListener(e -> {
                if (e.getValue() == Action.ActionType.GO_TO_QUESTION) {
                    add(questionsGrid);
                } else {
                    remove(questionsGrid);
                }
            });
            add(actionTypeComboBox);

            questionsGrid = new Grid<>(); // Again, replace '?' with 'Question' if you have that class.
            // Fetch questions and set them to the grid
            // questionsGrid.setItems(fetchQuestions());
        }

        // Placeholder method for fetching questions, replace with actual logic
        private List<?> fetchQuestions() {
            // Implement this method to get the list of questions from the backend.
            // Replace '?' with the type of your Question.
            return new ArrayList<>();
        }
    }
}
