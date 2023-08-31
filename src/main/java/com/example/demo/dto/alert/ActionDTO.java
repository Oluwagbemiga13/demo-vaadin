package com.example.demo.dto.alert;

import com.example.demo.entity.alerts.Action;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ActionDTO {

    private Long id;
    private Action.ActionType type;
    private String description;

    private QuestionDTO targetQuestion;

    private String logMessage;
    private String redirectUrl;

    private AnswerDTO answer;

}
