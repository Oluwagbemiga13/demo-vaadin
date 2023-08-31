package com.example.demo.dto.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerDTO {
    private Long id;
    private String name;
    private String answer;
    private boolean severe;
    private QuestionDTO question;
    private QuestionDTO nextQuestion;

    // getters and setters
}

