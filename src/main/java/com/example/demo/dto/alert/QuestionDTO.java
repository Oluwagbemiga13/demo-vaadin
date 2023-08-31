package com.example.demo.dto.alert;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestionDTO {
    private Long id;
    private String name;
    private List<AnswerDTO> possibleAnswers;
    private String uiType;
    private AnswerDTO selectedAnswer;
    private String text;

    // getters and setters
}
