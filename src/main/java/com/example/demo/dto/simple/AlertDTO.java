package com.example.demo.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlertDTO {
    private Long id;
    private QuestionDTO question;
    private String message;
    private String severity;

    // getters and setters
}

