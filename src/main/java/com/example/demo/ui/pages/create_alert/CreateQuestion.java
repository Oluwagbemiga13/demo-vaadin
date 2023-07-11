package com.example.demo.ui.pages.create_alert;

import com.example.demo.dto.simple.AlertDTO;
import com.example.demo.dto.simple.AnswerDTO;
import com.example.demo.dto.simple.QuestionDTO;
import com.example.demo.entity.simple.Question;
import com.example.demo.service.simple.AlertService;
import com.example.demo.service.simple.AnswerService;
import com.example.demo.service.simple.QuestionService;
import com.example.demo.ui.tool.ButtonInitializer;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Route("create-question")
@RequiredArgsConstructor
@Slf4j
public class CreateQuestion extends VerticalLayout {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AlertService alertService;

    private TextField questionName;

    private final ButtonInitializer buttonInitializer;
    private TextArea questionText;
    private ComboBox<String> uiType;

    @PostConstruct
    void init() {
        add(buttonInitializer.createActButton("TEST", this::testDTOs, "250px"));
        add(buttonInitializer.createActButton("Save Question", this::sendToBE, "250px"));

        questionName = new TextField("Question Name");
        add(questionName);

        questionText = new TextArea("Question Text");
        add(questionText);

        uiType = new ComboBox<>("UI Types");
        uiType.setItems("YES_NO", "IN_NUM", "IN_TEXT", "SCALE");
        add((uiType));


    }

    private QuestionDTO createQuestionDTO(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName(questionName.getValue());
        questionDTO.setText(questionText.getValue());
        questionDTO.setUiType(uiType.getValue());
        return questionDTO;
    }

    void sendToBE(){
        QuestionDTO questionDTO = createQuestionDTO();
        log.info(questionDTO.getName() + " was send to BE");
        log.info(questionService.save(questionDTO).toString() + " was retrieved from BE");

    }

    void testDTOs() {
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setName("Some Question?");
//
//        // convert to entity and save
//        questionService.save(questionDTO);
//
//        // create an answer DTO
//        AnswerDTO answerDTO = new AnswerDTO();
//        answerDTO.setName("Some Answer");
//        answerDTO.setQuestion(questionDTO); // set the question DTO
//
//        // convert to entity and save
//        answerService.save(answerDTO);
//
//        // create an alert DTO
//        AlertDTO alertDTO = new AlertDTO();
//        alertDTO.setMessage("Some Alert");
//        alertDTO.setSeverity("High");
//        alertDTO.setQuestion(questionDTO); // set the question DTO
//
//        // convert to entity and save
//        alertService.save(alertDTO);
//
//        QuestionDTO question = questionService.findAll().get(0);
//
//        AnswerDTO answer = question.getPossibleAnswers().get(0);
//
//        AlertDTO alert = alertService.findAll().get(0);
//
//        log.info("Question : " + question.getName());
//        log.info("Answer : " + answer.getName());
//        log.info("Alert" + alert.getMessage());
/////

//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setName("Some Question?");
//
//        // convert to entity and save
//        questionDTO = questionService.save(questionDTO);
//
//        // create an answer DTO
//        AnswerDTO answerDTO = new AnswerDTO();
//        answerDTO.setName("Some Answer");
//        answerDTO.setQuestion(questionDTO); // set the question DTO
//
//        // convert to entity and save
//        answerService.save(answerDTO);
//
//        // create an alert DTO
//        AlertDTO alertDTO = new AlertDTO();
//        alertDTO.setMessage("Some Alert");
//        alertDTO.setSeverity("High");
//        alertDTO.setQuestion(questionDTO); // set the question DTO
//
//        // convert to entity and save
//        alertService.save(alertDTO);
//
//        QuestionDTO question = questionService.findAll().get(0);
//
//        AnswerDTO answer = question.getPossibleAnswers().get(0);
//
//        AlertDTO alert = alertService.findAll().get(0);
//
//        log.info("Question : " + question.getName());
//        log.info("Answer : " + answer.getName());
//        log.info("Alert" + alert.getMessage());

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName("Some Question?");

        // create an answer DTO
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setName("Some Answer");

        // set the answerDTO to the questionDTO
        questionDTO.setPossibleAnswers(Collections.singletonList(answerDTO));

        // save the questionDTO (which also saves the answerDTOs)
        questionDTO = questionService.save(questionDTO);

        // create an alert DTO
        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setMessage("Some Alert");
        alertDTO.setSeverity("High");
        alertDTO.setQuestion(questionDTO); // set the question DTO

        // save the alertDTO
        AlertDTO alertDTO1 = alertService.save(alertDTO);

        log.info("Message from alert : " + alertDTO1.getMessage());
    }


}
