package com.example.demo.dto.join;

import com.example.demo.dto.simple.PartDTO;
import com.example.demo.dto.simple.SymptomDTO;

public class SymptomPartDTO extends JoinItemDTO {

    private PartDTO part;
    private SymptomDTO symptom;

    public SymptomPartDTO(PartDTO part, SymptomDTO symptom) {
        super();
        this.part = part;
        this.symptom = symptom;
        setFirstDTO(symptom);
        setSecondDTO(part);
        setName(getFullName());
    }
}
