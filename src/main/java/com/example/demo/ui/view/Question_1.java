package com.example.demo.ui.view;

import com.example.demo.dto.alert.QuestionDTO;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.alert.AnswerService;
import com.example.demo.service.alert.QuestionService;
import com.example.demo.ui.tool.ButtonInitializer;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Question_1 extends VerticalLayout {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AlertService alertService;

    private TextField questionName;

    private final ButtonInitializer buttonInitializer;
    private TextArea questionText;
    private ComboBox<String> uiType;

    @PostConstruct
    void init() {
        add(buttonInitializer.createActButton("Save Question", this::sendToBE, "250px"));

        questionName = new TextField("Question Name");
        add(questionName);

        questionText = new TextArea("Question Text");
        add(questionText);

        uiType = new ComboBox<>("UI Types");
        uiType.setItems("YES_NO", "IN_NUM", "IN_TEXT", "SCALE");
        add((uiType));
    }

    private QuestionDTO createQuestionDTO() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName(questionName.getValue());
        questionDTO.setText(questionText.getValue());
        questionDTO.setUiType(uiType.getValue());
        return questionDTO;
    }

    void sendToBE() {
        QuestionDTO questionDTO = createQuestionDTO();
        log.info(questionDTO.getName() + " was send to BE");
        log.info(questionService.save(questionDTO).toString() + " was retrieved from BE");

    }
}
