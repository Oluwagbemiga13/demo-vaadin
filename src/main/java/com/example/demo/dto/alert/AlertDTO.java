package com.example.demo.dto.alert;

import com.example.demo.dto.join.OrganSymptomDTO;
import com.example.demo.dto.join.PartOrganDTO;
import com.example.demo.dto.join.SymptomPartDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AlertDTO {
    private Long id;
    private QuestionDTO question;
    private String message;
    private String severity;
    private OrganSymptomDTO organSymptom;
    private PartOrganDTO partOrgan;
    private SymptomPartDTO symptomPart;
}

