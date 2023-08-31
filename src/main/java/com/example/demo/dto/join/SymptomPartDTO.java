package com.example.demo.dto.join;

import com.example.demo.dto.simple.PartDTO;
import com.example.demo.dto.simple.SymptomDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SymptomPartDTO implements JoinItemDTO<SymptomDTO,PartDTO> {

    private Long id;
    private PartDTO part;
    private SymptomDTO symptom;

    public SymptomPartDTO(PartDTO part, SymptomDTO symptom) {
        super();
        this.part = part;
        this.symptom = symptom;
    }

    @Override
    public String getName() {
        return getFullName();
    }

    @Override
    public String getFullName() {
        return symptom.getName() + "+" + part.getName();
    }

    @Override
    public SymptomDTO getFirstDTO() {
        return symptom;
    }

    @Override
    public PartDTO getSecondDTO() {
        return part;
    }
}
