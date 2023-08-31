package com.example.demo.ui.dialogs;

import com.example.demo.dto.alert.AnswerDTO;
import com.example.demo.dto.alert.QuestionDTO;
import com.example.demo.entity.alerts.Answer;
import com.example.demo.entity.alerts.Action; // Assuming this is where Action and ActionType are.
import com.example.demo.entity.alerts.Question;
import com.example.demo.service.alert.AnswerService;
import com.example.demo.service.alert.QuestionService;
import com.example.demo.ui.tool.GridManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class CreateQuestionDialog extends Dialog {

    private TextField nameField;
    private TextField textField;
    private List<AnswerLayout> answerLayouts;

    private final GridManager gridManager;


    private final QuestionService questionService;

    private final AnswerService answerService;


    @Autowired
    public CreateQuestionDialog(GridManager gridManager, QuestionService questionService, AnswerService answerService) {
        this.gridManager = gridManager;
        this.questionService = questionService;
        this.answerService = answerService;
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

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName(nameField.getValue());
        questionDTO.setId(new Question().getId());
        questionDTO.setText(textField.getValue());
        List<AnswerDTO> answerDTOS = answerLayouts.stream()
                        .map(i -> (AnswerDTO) i.createAnswerDTO(questionDTO))
                                .toList();
        questionDTO.setPossibleAnswers(answerDTOS);

        QuestionDTO questionDTO1 = questionService.save(questionDTO);
        log.info("Entity saved in DB : {}", questionDTO1.toString());


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

            questionsGrid = gridManager.createLonelyGrid(questionService,new String[]{"name"});// Again, replace '?' with 'Question' if you have that class.
            // Fetch questions and set them to the grid
            // questionsGrid.setItems(fetchQuestions());
        }

        protected AnswerDTO createAnswerDTO(QuestionDTO questionDTO){
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setAnswer(answerTextField.getValue());
            answerDTO.setName(answerNameField.getValue());
            answerDTO.setId(new Answer().getId());
            answerDTO.setQuestion(questionDTO);

//            TODO: taken care of in service -> check for best practices.
//            answerService.save(answerDTO);

            return answerDTO;

        }

        // Placeholder method for fetching questions, replace with actual logic
        private List<QuestionDTO> fetchQuestions() {
            // Implement this method to get the list of questions from the backend.
            // Replace '?' with the type of your Question.

            return questionService.findAll();
        }
    }
}
